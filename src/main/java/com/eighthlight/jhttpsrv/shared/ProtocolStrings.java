package com.eighthlight.jhttpsrv.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProtocolStrings {
    public static final String HTTP_METHOD_GET  = "GET";
    public static final String HTTP_METHOD_POST = "POST";

    public static final String METHOD = "Method";
    public static final String URL = "URL";
    public static final String PROTOCOL_VERSION = "Protocol-Version";

    public static final String HOST = "Host";
    public static final String CONNECTION = "Connection";
    public static final String ACCEPT = "Accept";
    public static final String USER_AGENT = "User-Agent";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";

    public static final List<String> RESPONSE_HEADER_KEYS;
    static {
        List<String> myList = new ArrayList<String>();

        myList.add(CONTENT_LENGTH);
        myList.add(CONTENT_TYPE);
        myList.add(LOCATION);

        RESPONSE_HEADER_KEYS = Collections.unmodifiableList(myList);
    }
}