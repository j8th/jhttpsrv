package com.eighthlight.jhttpsrv.testmessage.chrome;

public class GETformResponse {
    public final static String STATUS_LINE = "HTTP/1.1 200 OK\r\n";

    public final static String HEADERS =
            "Content-Length: 222\r\n" +
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