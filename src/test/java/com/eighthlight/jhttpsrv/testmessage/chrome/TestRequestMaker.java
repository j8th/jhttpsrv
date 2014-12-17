package com.eighthlight.jhttpsrv.testmessage.chrome;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by jason on 12/17/14.
 */
public class TestRequestMaker {

    public final static Request fromString(String requestString) throws IOException {
        RequestParser parser = new RequestParser();
        InputStream is = new ByteArrayInputStream(requestString.getBytes(StandardCharsets.UTF_8));
        return parser.parseInputStream(is);
    }

}
