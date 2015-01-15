package com.eighthlight.jhttpsrv.util;

public class ArrayUtils {
    public static byte[] getArrayRange(byte[] source, Integer start, Integer end) {
        if(start == null)
            start = 0;

        if(end == null)
            end = source.length - 1;

        if(start < 0 || end < 0)
            throw new IllegalArgumentException("Neither start nor end may be negative Integers.");

        int length = end - start + 1;
        byte[] result = new byte[length];
        System.arraycopy(source, start, result, 0, length);
        return result;
    }

    public static byte[] getArrayTail(byte[] source, Integer num) {
        if(num == null)
            num = source.length;

        if(num < 0)
            throw new IllegalArgumentException("num cannot be a negative Integer.");

        int start = source.length - num;
        byte[] result = new byte[num];
        System.arraycopy(source, start, result, 0, num);
        return result;
    }
}
