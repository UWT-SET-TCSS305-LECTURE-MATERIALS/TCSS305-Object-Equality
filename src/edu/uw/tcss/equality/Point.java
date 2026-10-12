/*
 * TCSS 305 - Week 2 lecture demo: object equality.
 */

package edu.uw.tcss.equality;

import java.util.Objects;

/**
 * A point on a screen grid, where coordinates are never negative.
 *
 * <p>Point is mutable on purpose. The setters let the lecture show aliasing
 * (two variables, one object) and what happens to a mutable object stored in a
 * HashSet when its state changes.
 *
 * <p>It also validates its input, so the lecture can demo testing for
 * exceptions: a negative coordinate is an IllegalArgumentException, and copying
 * a null Point is a NullPointerException.
 *
 * @author Charles Bryan
 * @version Autumn 2026
 */
public class Point {

    /** Name of the x coordinate, for exception messages. */
    private static final String X_NAME = "x";

    /** Name of the y coordinate, for exception messages. */
    private static final String Y_NAME = "y";

    /** The x coordinate, never negative. */
    private int myX;

    /** The y coordinate, never negative. */
    private int myY;

    /**
     * Creates a point at the given coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalArgumentException if x or y is negative
     */
    public Point(final int x, final int y) {
        myX = requireNonNegative(x, X_NAME);
        myY = requireNonNegative(y, Y_NAME);
    }

    /**
     * Creates a point at the same coordinates as another point.
     *
     * @param other the point to copy
     * @throws NullPointerException if other is null
     */
    public Point(final Point other) {
        // There's a lot going on in this one line, so let's take it apart.
        //
        // this(...) runs the other constructor, Point(int, int), so the setup and
        // the negative-number check stay in one place. It has traditionally had
        // to be the first statement in the constructor. Java 25 loosens that rule
        // a bit, but you'll almost always see it first.
        //
        // Objects.requireNonNull(other, message) throws a NullPointerException
        // with our message if other is null. If it isn't null, the method hands
        // other back to us, which is why we can put .myX right on the end of it.
        //
        // Java evaluates arguments left to right, so the null check happens before
        // other.myY is read. If the check were on the second argument, other.myX
        // would blow up first and you'd get the JVM's message instead of ours.
        //
        // other.myX is a private field of a different Point, and Java allows it.
        // Private is about the class, not the individual object.
        //
        // A null here means the caller has a bug. We throw instead of making a
        // (0, 0) point, which would hide that bug until much later.
        this(Objects.requireNonNull(other, "other must not be null").myX, other.myY);
    }

    /**
     * Returns the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return myX;
    }

    /**
     * Returns the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return myY;
    }

    /**
     * Changes the x coordinate.
     *
     * @param x the new x coordinate
     * @throws IllegalArgumentException if x is negative
     */
    public void setX(final int x) {
        myX = requireNonNegative(x, X_NAME);
    }

    /**
     * Changes the y coordinate.
     *
     * @param y the new y coordinate
     * @throws IllegalArgumentException if y is negative
     */
    public void setY(final int y) {
        myY = requireNonNegative(y, Y_NAME);
    }

    /**
     * Returns the value unchanged, or throws if it is negative.
     *
     * @param value the coordinate to check
     * @param name the coordinate's name, for the exception message
     * @return value, when it is not negative
     * @throws IllegalArgumentException if value is negative
     */
    private static int requireNonNegative(final int value, final String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must not be negative: " + value);
        }
        return value;
    }

    // ===================== equals: two versions =====================
    //
    // Version 1 (getClass) is live. Version 2 (instanceof) is commented out.
    // Only one can be live at a time, or the class has two equals methods and
    // won't compile.
    //
    // To swap them, select just the method lines of each version (from @Override
    // to the closing brace) and press Cmd+/ (Ctrl+/ on Windows). The commented
    // lines are written the way IntelliJ writes them, with // in the first
    // column, so they toggle back in at the right indentation and Checkstyle
    // stays quiet. The project's code style pins that setting.
    //
    // To see Point with no equals at all, comment out both. It then inherits
    // Object's equals, which is just ==, and Checkstyle warns that hashCode has
    // no matching equals.
    //
    // Demo: change the last line of the live version to compare only x, rerun,
    // and testEqualsDifferentState fails.
    //
    // ---- Version 1: getClass() ----
    //
    // Only an exact Point can be equal. A subclass object never equals a Point,
    // even with the same x and y.
    //
    // The upside is that symmetry can't break, even if a subclass like Point3D
    // adds a field and its own equals. It's also what the A1b and A1c specs ask
    // for: a StoreItem is never equal to a StoreBulkItem.
    //
    // The downside is that a subclass adding nothing at all (say it only
    // overrides toString, or a framework generates it as a proxy) still never
    // equals a Point with the same coordinates. You also write the null check
    // and the cast yourself.

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Point otherPoint = (Point) other;
        return myX == otherPoint.myX && myY == otherPoint.myY;
    }

    // ---- Version 2: instanceof ----
    //
    // A Point or any subclass of Point can be equal, as long as x and y match.
    // Effective Java (Item 10) recommends this one.
    //
    // Subclasses keep working anywhere a Point works, equality included, so
    // proxies and do-nothing subclasses compare the way you'd expect. The null
    // check is free, since null instanceof Point is false, and the pattern
    // variable (Point otherPoint) does the cast for you.
    //
    // The catch: if a subclass adds a field and overrides equals, symmetry
    // breaks. point.equals(point3D) is true, but point3D.equals(point) is false.
    // Making the class final avoids that, and so does having the "subclass" hold
    // a Point instead of extending it.
    //
    // For final classes and simple value types, use this one. Use getClass()
    // when a hierarchy really does add state, the way A1b's does.
//    @Override
//    public boolean equals(final Object other) {
//        if (this == other) {
//            return true;
//        }
//        return other instanceof Point otherPoint
//            && myX == otherPoint.myX && myY == otherPoint.myY;
//    }

    /**
     * Built from the same fields equals compares, so equal points always have
     * equal hash codes.
     *
     * @return a hash code for this point
     */
    @Override
    public int hashCode() {
        return Objects.hash(myX, myY);
    }

    @Override
    public String toString() {
        return "(" + myX + ", " + myY + ")";
    }
}
