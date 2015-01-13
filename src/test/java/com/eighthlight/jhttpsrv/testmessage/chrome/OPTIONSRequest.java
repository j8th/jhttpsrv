package com.eighthlight.jhttpsrv.testmessage.chrome;

public class OPTIONSRequest {
    public final static String REQUEST_LINE = "OPTIONS /method_options HTTP/1.1\r\n";

    public final static String HEADERS =
                    "Host: localhost\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36\r\n" +
                    "Accept-Language: en-US,en;q=0.8\r\n";

    public final static String EMPTY_LINE = "\r\n";

    public final static String ENTIRE_MESSAGE = REQUEST_LINE + HEADERS + EMPTY_LINE;
}