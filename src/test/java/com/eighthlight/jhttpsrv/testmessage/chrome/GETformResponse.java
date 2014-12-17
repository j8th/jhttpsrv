package com.eighthlight.jhttpsrv.testmessage.chrome;

/**
 * Created by jason on 12/11/14.
 */
public class GETformResponse {
    public final static String STATUS_LINE = "HTTP/1.1 200 OK\r\n";

    public final static String HEADERS =
            //"Date: Thu, 11 Dec 2014 20:07:02 GMT\r\n" +
            //"Server: Apache\r\n" +
            //"Last-Modified: Wed, 14 May 2014 23:00:27 GMT\r\n" +
            //"Accept-Ranges: bytes\r\n" +
            "Content-Length: 222\r\n" +
            //"Vary: Accept-Encoding\r\n" +
            //"Keep-Alive: timeout=10, max=500\r\n" +
            //"Connection: Keep-Alive\r\n" +
            "Content-Type: text/html\r\n";

    public final static String EMPTY_LINE = "\r\n";

    public final static String BODY =
    "<html>\n" +
    "<head>\n" +
    "<title>A Form</title>\n" +
    "</head>\n" +
    "<body>\n" +
            "<form method=\"post\" action=\"/formsubmit\">\n" +
                "<label for=\"name\">Your Name:</label>\n" +
                "<input id=\"name\" type=\"text\">\n" +
                "<button type=\"submit\">Submit</button>\n" +
            "</form>\n" +
    "</body>\n" +
    "</html>\n";

    public final static String ENTIRE_MESSAGE = STATUS_LINE + HEADERS + EMPTY_LINE + BODY;

}
