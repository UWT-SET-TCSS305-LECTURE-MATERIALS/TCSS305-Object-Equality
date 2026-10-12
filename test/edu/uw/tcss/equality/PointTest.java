/*
 * TCSS 305 - Week 2 lecture demo: object equality.
 *
 * Starter for live coding. One test is written as a model; the rest are
 * written in lecture. The finished version is in reference/PointTestFinished.java.
 */

package edu.uw.tcss.equality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the equals and hashCode contracts of {@link Point}.
 *
 * @author Charles Bryan
 * @version Autumn 2026
 */
class PointTest {

    /** X coordinate shared by the tests below. */
    private static final int X = 10;

    /** Y coordinate shared by the tests below. */
    private static final int Y = 20;

    /** A y coordinate different from Y. */
    private static final int OTHER_Y = 99;

    /**
     * Reflexive: a point is equal to itself.
     */
    @Test
    void testEqualsIsReflexive() {
        // Arrange
        final Point point = new Point(X, Y);

        // Act
        final boolean result = point.equals(point);

        // Assert
        assertTrue(result, "A point should be equal to itself");
    }

    /*
     * Written live in lecture, one contract rule per test:
     *
     *   testEqualsIsSymmetric          assert in BOTH orders
     *   testEqualsWithNull             assertNotEquals(point, null), point first
     *   testEqualsWithDifferentType
     *   testEqualsSameState
     *   testEqualsDifferentState       the one that catches the broken equals
     *   testHashCodeEqualObjects
     *   testHashCodeIsConsistent
     *
     * assertEquals and assertNotEquals are already imported for them.
     */

    /**
     * Placeholder that keeps the unused imports in use until the live tests
     * are written. Delete it once testEqualsSameState exists.
     */
    @Test
    void testImportsCompile() {
        assertEquals(X, new Point(X, Y).getX(), "getX should return the x given");
        assertNotEquals(Y, OTHER_Y, "the test constants should differ");
    }
}
