package com.eighthlight.jhttpsrv.testmessage.chrome;

public class POSTFormRequest {
    public final static String REQUEST_LINE = "POST /formsubmit HTTP/1.1\r\n";

    public final static String HEADERS =
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 43\r\n" +
            "Cache-Control: no-cache\r\n" +
            "Origin: chrome-extension://fdmmgilgnpjigdojojpjoooidkmcomcm\r\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\r\n" +
            "Content-Type: application/x-www-form-urlencoded\r\n" +
            "Accept: */*\r\n" +
            "Accept-Encoding: gzip, deflate\r\n" +
            "Accept-Language: en-US,en;q=0.8\r\n";


    public final static String EMPTY_LINE = "\r\n";

    public final static String BODY = "mykey=myval&weather=cloudy&city=Chicago";

    public final static String ENTIRE_MESSAGE = REQUEST_LINE + HEADERS + EMPTY_LINE + BODY;
}