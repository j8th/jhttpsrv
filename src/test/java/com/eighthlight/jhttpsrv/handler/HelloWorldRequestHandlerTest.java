package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
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
        handler = new HelloWorldRequestHandler();
        request = TestRequestMaker.fromString(GETHelloworldRequest.ENTIRE_MESSAGE);
        builder = new ResponseBuilder();
    }

    @Test
    public void testRun() throws Exception {
        Response response = handler.run(request);
        byte[] expected = GETHelloworldResponse.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8);

        Assert.assertArrayEquals(expected, builder.buildResponse(response));
    }
}