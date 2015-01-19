package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.net.URL;
import java.util.Arrays;

public class Request {
    private String method;
    private URL url;
    private RequestHeader header;
    private RequestBody body;

    public Request(String method, URL url, RequestHeader header, RequestBody body) {
        this.method = method;
        this.url = url;
        this.header = header;
        this.body = body;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public RequestBody getBody() {
        return body;
    }

    public String getURLPath() {
        return url.getPath();
    }

    public String getURLQuery() {
        return url.getQuery();
    }

    public String getMethod() {
        return method;
    }

    public String getRequestLine() {
        return String.format("%s %s HTTP/1.1", method, url.getPath());
    }

    public boolean isValid() {
        if(!Arrays.asList(ProtocolStrings.HTTP_METHODS).contains(method))
            return false;
        if(url ==null)
            return false;
        return true;
    }
}