package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.util.Map;

public class Request {
    private String method;
    private String url;
    private RequestHeader header;

    public Request(Map<String, String> myRequestLine, RequestHeader myHeader) {
        method = myRequestLine.getOrDefault(ProtocolStrings.METHOD, "");
        url = myRequestLine.getOrDefault(ProtocolStrings.URL, "");
        header = myHeader;
    }

    public Request(Map<String, String> myRequestLine, RequestHeader myHeader, RequestBody myBody) {
        method = myRequestLine.getOrDefault(ProtocolStrings.METHOD, "");
        url = myRequestLine.getOrDefault(ProtocolStrings.URL, "");
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

    public boolean isEmpty() {
        if(method != "" || url != "" || !header.isEmpty())
            return false;
        return true;
    }
}