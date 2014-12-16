package com.eighthlight.jhttpsrv.shared;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 12/11/14.
 */
public class StatusCodes {
    /* Numeric Codes */
    public static final int OK = 200;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int NOT_FOUND = 404;

    /* Reason Phrases */
    public static final String OK_PHRASE = "OK";
    public static final String TEMPORARY_REDIRECT_PHRASE = "Temporary Redirect";
    public static final String NOT_FOUND_PHRASE = "Not Found";

    /* Mapping Between Codes and Phrases */
    private static final Map<Integer, String> CODES_TO_PHRASES;
    static {
        Map<Integer, String> myMap = new HashMap<Integer, String>();

        myMap.put(OK, OK_PHRASE);
        myMap.put(TEMPORARY_REDIRECT, TEMPORARY_REDIRECT_PHRASE);
        myMap.put(NOT_FOUND, NOT_FOUND_PHRASE);

        CODES_TO_PHRASES = Collections.unmodifiableMap(myMap);
    }

    public static String CodeToPhrase(int code) {
        return CODES_TO_PHRASES.get(code);
    }
}
