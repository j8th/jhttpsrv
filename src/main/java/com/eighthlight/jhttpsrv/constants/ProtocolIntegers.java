package com.eighthlight.jhttpsrv.constants;

public class ProtocolIntegers {
    public static final int CR = 13;
    public static final int LF = 10;
    public static final int[] END_OF_LINE = {CR, LF};
    public static final int[] END_OF_HTTP_HEADER = {CR, LF, CR, LF};

    public static final int LIMIT_HEADER_FIELD_SIZE = 8192;
}