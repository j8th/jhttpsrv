package com.eighthlight.jhttpsrv.testmessage.chrome;

public class GETparamsRequest {
    public final static String REQUEST_LINE = "GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1\r\n";

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