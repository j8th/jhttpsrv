package com.eighthlight.jhttpsrv.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StatusCodes {
    public static final int OK = 200;
    public static final int NO_CONTENT = 204;
    public static final int PARTIAL_CONTENT = 206;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;

    public static final String OK_PHRASE = "OK";
    public static final String NO_CONTENT_PHRASE = "No Content";
    public static final String PARTIAL_CONTENT_PHRASE = "Partial Content";
    public static final String TEMPORARY_REDIRECT_PHRASE = "Temporary Redirect";
    public static final String BAD_REQUEST_PHRASE = "Bad Request";
    public static final String UNAUTHORIZED_PHRASE = "Unauthorized";
    public static final String NOT_FOUND_PHRASE = "Not Found";
    public static final String METHOD_NOT_ALLOWED_PHRASE = "Method Not Allowed";

    private static final Map<Integer, String> CODES_TO_PHRASES;
    static {
        Map<Integer, String> myMap = new HashMap<Integer, String>();

        myMap.put(OK, OK_PHRASE);
        myMap.put(NO_CONTENT, NO_CONTENT_PHRASE);
        myMap.put(PARTIAL_CONTENT, PARTIAL_CONTENT_PHRASE);
        myMap.put(TEMPORARY_REDIRECT, TEMPORARY_REDIRECT_PHRASE);
        myMap.put(BAD_REQUEST, BAD_REQUEST_PHRASE);
        myMap.put(UNAUTHORIZED, UNAUTHORIZED_PHRASE);
        myMap.put(NOT_FOUND, NOT_FOUND_PHRASE);
        myMap.put(METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED_PHRASE);

        CODES_TO_PHRASES = Collections.unmodifiableMap(myMap);
    }

    public static String CodeToPhrase(int code) {
        return CODES_TO_PHRASES.get(code);
    }
}