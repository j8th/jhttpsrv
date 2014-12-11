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
        String myHeader;

        host = headers.get(ProtocolStrings.HOST);
        connection = headers.get(ProtocolStrings.CONNECTION);

        myHeader = headers.get(ProtocolStrings.ACCEPT);
        accept = myHeader == null ? null : myHeader.split(",");

        userAgent = headers.get(ProtocolStrings.USER_AGENT);

        myHeader = headers.get(ProtocolStrings.ACCEPT_ENCODING);
        acceptEncoding = myHeader == null ? null : myHeader.split(", ");

        myHeader = headers.get(ProtocolStrings.ACCEPT_LANGUAGE);
        acceptLanguage = myHeader == null ? null : myHeader.split(",");
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
