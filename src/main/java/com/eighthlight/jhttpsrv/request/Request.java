package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.util.Arrays;
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

    public String getURL() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public boolean isValid() {
        if(!Arrays.asList(ProtocolStrings.HTTP_METHODS).contains(method))
            return false;
        if(url == "")
            return false;
        return true;
    }
}