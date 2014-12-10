package com.eighthlight.jhttpsrv.shared;

/**
 * Created by jason on 12/10/14.
 */
public class ProtocolIntegers {
    public static final int CR = 13;
    public static final int LF = 10;
    public static final int[] END_OF_LINE = {CR, LF};
    public static final int[] END_OF_HTTP_HEADER = {CR, LF, CR, LF};

    /**
     * Take a tip from Apache.
     * Reference:
     * http://stackoverflow.com/questions/686217/maximum-on-http-header-values
     * http://httpd.apache.org/docs/2.2/mod/core.html#limitrequestfieldsize
     *
     * But when we do the math, we get 1024 * 8 = 8192, not 8190 bytes, so we use that here.
     */
    public static final int LIMIT_HEADER_FIELD_SIZE = 8192;
    public static final int LIMIT_BODY_FIELD_SIZE = 8192;
}
