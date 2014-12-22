package com.eighthlight.jhttpsrv.testmessage.chrome;

public class GETHelloworldResponse {
    public final static String STATUS_LINE = "HTTP/1.1 200 OK\r\n";

    public final static String HEADERS =
            "Content-Length: 93\r\n" +
            "Content-Type: text/html\r\n";

    public final static String EMPTY_LINE = "\r\n";

    public final static String BODY =
    "<html>\n" +
    "<head>\n" +
    "<title>Hello World</title>\n" +
    "</head>\n" +
    "<body>\n" +
    "<h1>Hello World</h1>\n" +
    "</body>\n" +
    "</html>\n";

    public final static String ENTIRE_MESSAGE = STATUS_LINE + HEADERS + EMPTY_LINE + BODY;
}