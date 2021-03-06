package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.util.Map;

public class RequestHeader {
    private String[] accept;
    private String[] acceptEncoding;
    private String[] acceptLanguage;
    private String authorization;
    private String connection;
    private int contentlength;
    private String host;
    private String range;
    private String userAgent;

    public RequestHeader(Map<String, String> headers) {
        String myHeader;

        myHeader = headers.get(ProtocolStrings.ACCEPT);
        accept = myHeader == null ? new String[0] : myHeader.split(",");

        myHeader = headers.get(ProtocolStrings.ACCEPT_ENCODING);
        acceptEncoding = myHeader == null ? new String[0] : myHeader.split(", ");

        myHeader = headers.get(ProtocolStrings.ACCEPT_LANGUAGE);
        acceptLanguage = myHeader == null ? new String[0] : myHeader.split(",");

        myHeader = headers.get(ProtocolStrings.AUTHORIZATION);
        authorization = myHeader == null ? "" : myHeader;

        myHeader = headers.get(ProtocolStrings.CONNECTION);
        connection = myHeader == null ? "" : myHeader;

        myHeader = headers.get(ProtocolStrings.CONTENT_LENGTH);
        contentlength = myHeader == null ? 0 : Integer.parseInt(myHeader);

        myHeader = headers.get(ProtocolStrings.HOST);
        host = myHeader == null ? "" : myHeader;

        myHeader = headers.get(ProtocolStrings.RANGE);
        range = myHeader == null ? "" : myHeader;

        myHeader = headers.get(ProtocolStrings.USER_AGENT);
        userAgent = myHeader == null ? "" : myHeader;
    }

    public String[] getAccept() {
        return accept;
    }

    public String[] getAcceptEncoding() {
        return acceptEncoding;
    }

    public String[] getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getConnection() {
        return connection;
    }

    public int getContentLength() {
        return contentlength;
    }

    public String getHost() {
        return host;
    }

    public String getRange() {
        return range;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public boolean isEmpty() {
        if(
            host != "" ||
            connection != "" ||
            contentlength != 0 ||
            accept.length > 0 ||
            userAgent != "" ||
            acceptEncoding.length > 0 ||
            acceptLanguage.length > 0
        ) {
            return false;
        }
        return true;
    }
}