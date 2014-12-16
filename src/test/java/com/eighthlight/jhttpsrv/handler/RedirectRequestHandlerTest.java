package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedirectRequestHandlerTest {
    private RedirectRequestHandler handler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        handler = new RedirectRequestHandler();
        request = GETRequestChrome.asObj();
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