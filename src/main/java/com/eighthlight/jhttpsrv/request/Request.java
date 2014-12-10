package com.eighthlight.jhttpsrv.request;

import java.util.Map;

/**
 * Created by jason on 12/8/14.
 */
public class Request {
    private static final String HTTP_METHOD_GET  = "GET";
    private static final String HTTP_METHOD_POST = "POST";

    // The parts of the Request-Line.
    private String method;
    private String url;
    //private String protocolVersion;

    private Header header;

    public Request(Map<String, String> myRequestLine, Header myHeader, Body myBody) {
        method = myRequestLine.get("Method");
        url = myRequestLine.get("URL");
        //protocolVersion = myRequestLine.get("ProtocolVersion");

        header = myHeader;
    }

    public Header getHeader() {
        return header;
    }

    public boolean isGET() {
        return method == HTTP_METHOD_GET;
    }

    public boolean isPOST() {
        return method == HTTP_METHOD_POST;
    }
}
