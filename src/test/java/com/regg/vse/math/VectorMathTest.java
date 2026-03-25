package com.regg.vse.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VectorMathTest {

    @Test
    void dot_basic() {
        float[] a = {1f, 2f};
        float[] b = {3f, 4f};
        assertEquals(11f, VectorMath.dot(a, b), 1e-6);
    }

    @Test
    void l2Squared_basic() {
        float[] a = {1f, 2f};
        float[] b = {4f, 6f};
        assertEquals(25f, VectorMath.l2Squared(a, b), 1e-6);
    }

    @Test
    void cosine_orthogonal_is_zero() {
        float[] a = {1f, 0f};
        float[] b = {0f, 1f};
        assertEquals(0f, VectorMath.cosine(a, b), 1e-6);
    }

    @Test
    void cosine_identical_is_one() {
        float[] a = {3f, 4f};
        float[] b = {3f, 4f};
        assertEquals(1f, VectorMath.cosine(a, b), 1e-6);
    }

    @Test
    void cosine_zero_vector_returns_zero() {
        float[] a = {0f, 0f, 0f};
        float[] b = {1f, 2f, 3f};
        assertEquals(0f, VectorMath.cosine(a, b), 1e-6);
    }

    @Test
    void mismatched_lengths_throw() {
        float[] a = {1f};
        float[] b = {1f, 2f};
        assertThrows(IllegalArgumentException.class, () -> VectorMath.dot(a, b));
        assertThrows(IllegalArgumentException.class, () -> VectorMath.l2Squared(a, b));
        assertThrows(IllegalArgumentException.class, () -> VectorMath.cosine(a, b));
    }
}
