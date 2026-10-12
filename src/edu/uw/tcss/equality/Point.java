/*
 * TCSS 305 - Week 2 lecture demo: object equality.
 */

package edu.uw.tcss.equality;

import java.util.Objects;

/**
 * A point on an integer grid.
 *
 * <p>Point is mutable on purpose. The setters let the lecture show aliasing
 * (two variables, one object) and what happens to a mutable object stored in a
 * HashSet when its state changes.
 *
 * @author Charles Bryan
 * @version Autumn 2026
 */
public class Point {

    /** The x coordinate. */
    private int myX;

    /** The y coordinate. */
    private int myY;

    /**
     * Creates a point at the given coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(final int x, final int y) {
        myX = x;
        myY = y;
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
     */
    public void setX(final int x) {
        myX = x;
    }

    /**
     * Changes the y coordinate.
     *
     * @param y the new y coordinate
     */
    public void setY(final int y) {
        myY = y;
    }

    // ================= equals: two versions, both commented out =================
    //
    // With both commented out, Point inherits Object's equals, which is just ==.
    // Lecture: run PointTest first and watch the same-state tests fail, because two
    // Points made from the same numbers are different objects. Then uncomment ONE
    // version (never both: that is a duplicate method and will not compile).
    //
    // Demo once a version is live: change its last line to compare only x, rerun,
    // and watch testEqualsDifferentState fail.
    //
    // ---- Version 1: getClass() ----
    //
    // Equal only when other is exactly a Point. A subclass instance is never equal
    // to a Point, even one with the same x and y.
    //
    //   + Symmetry is safe even if a subclass adds a field and its own equals.
    //     Point3D can never equal a Point, in either direction.
    //   + Matches the A1b/A1c spec: a StoreItem is never equal to a StoreBulkItem.
    //   - Breaks substitutability. A subclass that adds nothing at all (one that
    //     only overrides toString, or a proxy a framework generates) is never equal
    //     to a Point with the same coordinates.
    //   - Needs an explicit null check and an explicit cast.
    //
    // @Override
    // public boolean equals(final Object other) {
    //     if (this == other) {
    //         return true;
    //     }
    //     if (other == null || getClass() != other.getClass()) {
    //         return false;
    //     }
    //
    //     final Point otherPoint = (Point) other;
    //     return myX == otherPoint.myX && myY == otherPoint.myY;
    // }
    //
    // ---- Version 2: instanceof ----
    //
    // Equal when other is a Point or any subclass of Point, with the same x and y.
    // This is the version Effective Java (Item 10) recommends.
    //
    //   + Respects substitutability: anywhere a Point works, a subclass works,
    //     equality included. Proxies and do-nothing subclasses compare correctly.
    //   + null instanceof Point is false, so the null check comes free.
    //   + The pattern variable (Point otherPoint) does the cast in the same step.
    //   - If a subclass adds a field AND overrides equals, symmetry breaks:
    //     point.equals(point3D) is true but point3D.equals(point) is false.
    //     The usual fix is to make the class final, or to have the subclass hold a
    //     Point (composition) instead of extending it.
    //
    // Why prefer it: most classes are final or never extended with new state, and
    // for those instanceof is shorter, null-safe and plays well with frameworks.
    // Reach for getClass() when a hierarchy really does add state, as A1b's does.
    //
    // @Override
    // public boolean equals(final Object other) {
    //     if (this == other) {
    //         return true;
    //     }
    //     return other instanceof Point otherPoint
    //         && myX == otherPoint.myX && myY == otherPoint.myY;
    // }

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
