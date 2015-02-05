package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETindexhtmlRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MethodNotAllowedRequestHandlerTest {
    private MethodNotAllowedRequestHandler methodNotAllowedRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        methodNotAllowedRequestHandler = new MethodNotAllowedRequestHandler();
        request = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);
    }

    @Test
    public void testRun() throws Exception {
        Response response = methodNotAllowedRequestHandler.run(request);

        assertEquals(StatusCodes.METHOD_NOT_ALLOWED, response.getStatusCode());
    }
}