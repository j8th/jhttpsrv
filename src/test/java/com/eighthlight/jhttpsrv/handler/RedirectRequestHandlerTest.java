package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETResponseChrome;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedirectRequestHandlerTest {
    private RedirectRequestHandler handler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        handler = new RedirectRequestHandler();
        request = TestRequestMaker.fromString(GETResponseChrome.ENTIRE_MESSAGE);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Response response = handler.run(request);
        Assert.assertEquals(StatusCodes.TEMPORARY_REDIRECT, response.getStatusCode());
        Assert.assertEquals("/time", response.getHeaders().getLocation());
    }
}