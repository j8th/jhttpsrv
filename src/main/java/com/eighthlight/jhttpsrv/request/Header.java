package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.util.Map;

/**
 * Created by jason on 12/8/14.
 */
public class Header {
    private String host;
    private String connection;
    private String[] accept;
    private String userAgent;
    private String[] acceptEncoding;
    private String[] acceptLanguage;

    public Header(Map<String, String> headers) {
        host = headers.get(ProtocolStrings.HOST);
        connection = headers.get(ProtocolStrings.CONNECTION);
        accept = headers.get(ProtocolStrings.ACCEPT).split(",");
        userAgent = headers.get(ProtocolStrings.USER_AGENT);
        acceptEncoding = headers.get(ProtocolStrings.ACCEPT_ENCODING).split(", ");
        acceptLanguage = headers.get(ProtocolStrings.ACCEPT_LANGUAGE).split(",");
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
