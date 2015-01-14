package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class Request {
    private String method;
    private URL url;
    private RequestHeader header;
    private RequestBody body;

    public Request(Map<String, String> myRequestLine, RequestHeader myHeader, RequestBody myBody) {
        method = myRequestLine.getOrDefault(ProtocolStrings.METHOD, "");
        try {
            URL context = new URL("http://localhost:8080/");
            url = new URL(context, myRequestLine.getOrDefault(ProtocolStrings.URL, ""));
        } catch (MalformedURLException e) {
            url = null;
            e.printStackTrace();
        }
        header = myHeader;
        body = myBody;
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

    public boolean isValid() {
        if(!Arrays.asList(ProtocolStrings.HTTP_METHODS).contains(method))
            return false;
        if(url ==null)
            return false;
        return true;
    }
}