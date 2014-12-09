package com.eighthlight.jhttpsrv.parsers;

import com.eighthlight.jhttpsrv.httpmessages.HttpMessage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 12/8/14.
 */
public class HttpMessageParser {

    public Map<String, String> parseRequestLine(String myRequestLine) {
        HashMap<String, String> result = new HashMap<String, String>();
        String[] tokens = myRequestLine.trim().split(" ");
        result.put("Method", tokens[0]);
        result.put("URL", tokens[1]);
        result.put("Protocol-Version", tokens[2].substring(tokens[2].indexOf('/')+1));
        return result;
    }

    public Map<String, String> parseHeaders(String myHeaderString) {
        return new HashMap<String, String>();
    }

    public HttpMessage parseInputStream(InputStream myis) {
        return new HttpMessage();
    }

}