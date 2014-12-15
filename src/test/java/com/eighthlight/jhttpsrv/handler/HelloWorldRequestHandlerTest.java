package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETResponseChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HelloWorldRequestHandlerTest {
    private HelloWorldRequestHandler handler;
    private Request request;
    private ResponseBuilder builder;

    @Before
    public void setUp() throws Exception {
        RequestParser parser = new RequestParser();
        InputStream is = new ByteArrayInputStream(GETRequestChrome.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8));

        handler = new HelloWorldRequestHandler();
        request = parser.parseInputStream(is);
        builder = new ResponseBuilder();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Response response = handler.run(request);
        Assert.assertEquals(GETResponseChrome.ENTIRE_MESSAGE, builder.buildResponse(response));
    }
}