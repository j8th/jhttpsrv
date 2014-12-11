package com.eighthlight.jhttpsrv.testmessage.chrome;

/**
 * Created by jason on 12/8/14.
 */
public class GETRequestChrome {

    /**
     * The complete 4 strings that comprise the request, separated into their 4 parts.
     *
     * "[Request and Response messages] consist of a start-line, zero
     * or more header fields (also known as "headers"), an empty line (i.e.,
     * a line with nothing preceding the CRLF) indicating the end of the
     * header fields, and possibly a message-body."
     *
     * Reference:
     * http://tools.ietf.org/html/rfc2616#section-4.2
     */

    public final static String REQUEST_LINE = "GET /helloworld HTTP/1.1\r\n";

    public final static String HEADERS =
                    "Host: localhost\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36\r\n" +
                    "Accept-Language: en-US,en;q=0.8\r\n";

    public final static String EMPTY_LINE = "\r\n";



    public final static String EntireMessage = REQUEST_LINE + HEADERS + EMPTY_LINE;
}
