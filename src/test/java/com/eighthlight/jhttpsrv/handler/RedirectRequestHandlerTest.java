package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedirectRequestHandlerTest {
    private RedirectRequestHandler handler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        handler = new RedirectRequestHandler("http://localhost:8080/");
        request = TestRequestMaker.fromString(GETHelloworldResponse.ENTIRE_MESSAGE);
    }

    @Test
    public void testRun() throws Exception {
        Response response = handler.run(request);
        Assert.assertEquals(StatusCodes.TEMPORARY_REDIRECT, response.getStatusCode());
        Assert.assertEquals("http://localhost:8080/", response.getHeaders().getLocation());
    }
}