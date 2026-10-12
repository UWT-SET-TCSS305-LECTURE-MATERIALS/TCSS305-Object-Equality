/*
 * TCSS 305 - Week 2 lecture demo: object equality.
 *
 * Instructor reference: where PointTest ends up after the live coding.
 * This folder is excluded from the IntelliJ module, so nothing here compiles
 * or runs until you copy it into test/edu/uw/tcss/equality.
 *
 * TWO TESTS FAIL ON PURPOSE when you do (marked FAILS BY DESIGN):
 *   testNullCheckCatchesBrokenEquals   shows assertNotEquals(obj, null) catching a bug
 *   testMutatedKeyIsStillFound         shows a mutated HashSet key going missing
 */

package edu.uw.tcss.equality;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the equals and hashCode contracts of {@link Point}.
 *
 * @author Charles Bryan
 * @version Autumn 2026
 */
class PointTestFinished {

    /** X coordinate shared by the tests below. */
    private static final int X = 10;

    /** Y coordinate shared by the tests below. */
    private static final int Y = 20;

    /** A y coordinate different from Y. */
    private static final int OTHER_Y = 99;

    /** A new x coordinate for the mutation demo. */
    private static final int NEW_X = 500;

    // ============ equals contract ============

    /** Reflexive: a point is equal to itself. */
    @Test
    void testEqualsIsReflexive() {
        final Point point = new Point(X, Y);

        assertTrue(point.equals(point), "A point should be equal to itself");
    }

    /** Symmetric: if a equals b, then b equals a. Assert both orders. */
    @Test
    void testEqualsIsSymmetric() {
        final Point first = new Point(X, Y);
        final Point second = new Point(X, Y);

        assertAll("symmetry",
            () -> assertEquals(first, second, "first.equals(second) should be true"),
            () -> assertEquals(second, first, "second.equals(first) should be true"));
    }

    /** equals(null) returns false. The point goes first so its equals runs. */
    @Test
    void testEqualsWithNull() {
        final Point point = new Point(X, Y);

        assertNotEquals(point, null, "A point should not equal null");
    }

    /** An object of another type is never equal, and nothing is thrown. */
    @Test
    void testEqualsWithDifferentType() {
        final Point point = new Point(X, Y);

        assertNotEquals(point, "(10, 20)", "A point should not equal a String");
    }

    /** Different objects with the same state are equal. */
    @Test
    void testEqualsSameState() {
        assertEquals(new Point(X, Y), new Point(X, Y),
            "Points with the same coordinates should be equal");
    }

    /** Same x, different y: not equal. This catches an equals that ignores y. */
    @Test
    void testEqualsDifferentState() {
        assertNotEquals(new Point(X, Y), new Point(X, OTHER_Y),
            "Points with different y coordinates should not be equal");
    }

    // ============ hashCode contract ============

    /** Equal objects must have equal hash codes. */
    @Test
    void testHashCodeEqualObjects() {
        final Point first = new Point(X, Y);
        final Point second = new Point(X, Y);

        assertEquals(first.hashCode(), second.hashCode(),
            "Equal points must have equal hash codes");
    }

    /** Calling hashCode twice on an unchanged object gives the same value. */
    @Test
    void testHashCodeIsConsistent() {
        final Point point = new Point(X, Y);

        assertEquals(point.hashCode(), point.hashCode(),
            "hashCode should not change while the point does not change");
    }

    // ============ argument order trap ============

    /**
     * FAILS BY DESIGN. BrokenPoint claims to equal everything, including null.
     * With the object first, assertNotEquals calls broken.equals(null), gets
     * true, and fails: the bug is caught.
     */
    @Test
    void testNullCheckCatchesBrokenEquals() {
        final Point broken = new BrokenPoint(X, Y);

        assertNotEquals(broken, null, "equals(null) must return false");
    }

    /**
     * PASSES, and that is the problem. With null first, JUnit sees null and
     * compares by null-ness. broken.equals is never called, so the bug slips by.
     */
    @Test
    void testNullFirstNeverCallsEquals() {
        final Point broken = new BrokenPoint(X, Y);

        assertNotEquals(null, broken, "This passes without testing equals");
    }

    // ============ mutable key demo ============

    /**
     * FAILS BY DESIGN. The point is stored in the set's bucket for (10, 20).
     * After setX, its hash code points at a different bucket, so the set looks
     * in the wrong place and cannot find an object it holds.
     */
    @Test
    void testMutatedKeyIsStillFound() {
        final Point point = new Point(X, Y);
        final Set<Point> points = new HashSet<>();
        points.add(point);

        point.setX(NEW_X);

        assertTrue(points.contains(point), "The set should still find the point it holds");
    }

    /** A Point whose equals is broken: it says it equals anything at all. */
    private static final class BrokenPoint extends Point {

        /**
         * Creates a broken point.
         *
         * @param x the x coordinate
         * @param y the y coordinate
         */
        BrokenPoint(final int x, final int y) {
            super(x, y);
        }

        @Override
        public boolean equals(final Object other) {
            return true;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
