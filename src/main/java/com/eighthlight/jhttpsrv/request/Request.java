package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.util.Map;

/**
 * Created by jason on 12/8/14.
 */
public class Request {
    // The parts of the Request-Line.
    private String method;
    private String url;
    //private String protocolVersion;

    private RequestHeader header;

    public Request(Map<String, String> myRequestLine, RequestHeader myHeader) {
        method = myRequestLine.get(ProtocolStrings.METHOD);
        url = myRequestLine.get(ProtocolStrings.URL);

        header = myHeader;
    }

    public Request(Map<String, String> myRequestLine, RequestHeader myHeader, RequestBody myBody) {
        method = myRequestLine.get(ProtocolStrings.METHOD);
        url = myRequestLine.get(ProtocolStrings.URL);
        //protocolVersion = myRequestLine.get("ProtocolVersion");

        header = myHeader;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public boolean isGET() {
        return method.equals(ProtocolStrings.HTTP_METHOD_GET);
    }

    public boolean isPOST() {
        return method.equals(ProtocolStrings.HTTP_METHOD_POST);
    }

    public String getURL() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}
