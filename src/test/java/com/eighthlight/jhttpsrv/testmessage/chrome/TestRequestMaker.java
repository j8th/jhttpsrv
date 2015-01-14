package com.eighthlight.jhttpsrv.testmessage.chrome;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestRequestMaker {
    public final static Request fromString(String requestString) throws IOException {
        URL contextURL = new URL("http://localhost:8080/");
        RequestParser parser = new RequestParser(contextURL);
        InputStream is = new ByteArrayInputStream(requestString.getBytes(StandardCharsets.UTF_8));
        return parser.parseInputStream(is);
    }
}