package com.eighthlight.jhttpsrv.parser;

import com.eighthlight.jhttpsrv.request.Request;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 12/8/14.
 */
public class MessageParser {

    public static final String CRLF = "\r\n";

    public Map<String, String> parseRequestLine(String requestLine) {
        HashMap<String, String> result = new HashMap<String, String>();
        String[] tokens = requestLine.trim().split(" ");
        result.put("Method", tokens[0]);
        result.put("URL", tokens[1]);
        result.put("Protocol-Version", tokens[2].substring(tokens[2].indexOf('/')+1));
        return result;
    }

    public Map<String, String> parseHeaders(String headers) {
        HashMap<String, String> result = new HashMap<String, String>();
        String[] headerLines = headers.split(CRLF);
        String[] tokens;
        String key;
        String val;
        for(int i=0; i < headerLines.length; i++) {
            tokens = headerLines[i].split(":");
            key = tokens[0];
            val = tokens[1].trim();
            result.put(key, val);
        }
        return result;
    }

    public Request parseInputStream(InputStream myis) {
        return new Request();
    }

}