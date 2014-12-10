package com.eighthlight.jhttpsrv.request;

import java.util.Map;

/**
 * Created by jason on 12/8/14.
 */
public class HttpHeader {
    private String host;
    private String connection;
    private String[] accept;
    private String userAgent;
    private String[] acceptEncoding;
    private String[] acceptLanguage;

    public HttpHeader(Map<String, String> headers) {
        host = headers.get("Host");
        connection = headers.get("Connection");
        accept = headers.get("Accept").split(",");
        userAgent = headers.get("User-Agent");
        acceptEncoding = headers.get("Accept-Encoding").split(", ");
        acceptLanguage = headers.get("Accept-Language").split(",");
    }

    public String getHost() {
        return host;
    }

    public String getConnection() {
        return connection;
    }

    public String[] getAccept() {
        return accept;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String[] getAcceptEncoding() {
        return acceptEncoding;
    }

    public String[] getAcceptLanguage() {
        return acceptLanguage;
    }



}
