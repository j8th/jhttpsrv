package com.eighthlight.jhttpsrv.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayUtilsTest {
    private byte[] byteArray;


    @Before
    public void setUp() throws Exception {
        byteArray = new byte[] {8, 10, 55, 20, 40, 6, 2, 50, 30, 20, 68, 42, 101, -100, -10, 127, -127};
    }

    @Test
    public void testGetArrayRange_WithByteArray() throws Exception {
        byte[] expected;
        byte[] actual;

        expected = new byte[] {8, 10, 55, 20, 40};
        actual =  ArrayUtils.getArrayRange(byteArray, 0, 4);
        assertArrayEquals(expected, actual);

        expected = new byte[] {6, 2, 50};
        actual = ArrayUtils.getArrayRange(byteArray, 5, 7);
        assertArrayEquals(expected, actual);

        expected = new byte[] {55};
        actual = ArrayUtils.getArrayRange(byteArray, 2, 2);
        assertArrayEquals(expected, actual);

        expected = new byte[] {8, 10, 55};
        actual = ArrayUtils.getArrayRange(byteArray, null, 2);
        assertArrayEquals(expected, actual);

        expected = new byte[] {101, -100, -10, 127, -127};
        actual = ArrayUtils.getArrayRange(byteArray, 12, null);
        assertArrayEquals(expected, actual);

        expected = new byte[] {8, 10, 55, 20, 40, 6, 2, 50, 30, 20, 68, 42, 101, -100, -10, 127, -127};
        actual = ArrayUtils.getArrayRange(byteArray, null, null);
        assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetArrayRange_WithByteArray_NegativeStartThrowsException() {
        ArrayUtils.getArrayRange(byteArray, -1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetArrayRange_WithByteArray_NegativeEndThrowsException() {
        ArrayUtils.getArrayRange(byteArray, 2, -10);
    }

    @Test
    public void testGetArrayTail_WithByteArray() throws Exception {
        byte[] expected;
        byte[] actual;

        expected = new byte[] {-10, 127, -127};
        actual = ArrayUtils.getArrayTail(byteArray, 3);
        assertArrayEquals(expected, actual);

        expected = new byte[] {};
        actual = ArrayUtils.getArrayTail(byteArray, 0);
        assertArrayEquals(expected, actual);

        expected = new byte[] {8, 10, 55, 20, 40, 6, 2, 50, 30, 20, 68, 42, 101, -100, -10, 127, -127};
        actual = ArrayUtils.getArrayTail(byteArray, null);
        assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetArrayTail_WithByteArray_NegativeArgumentThrowsException() {
        ArrayUtils.getArrayTail(byteArray, -10);
    }
}