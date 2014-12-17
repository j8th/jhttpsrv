package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETformRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETformResponse;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FormRequestHandlerTest {
    private FormRequestHandler formRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        formRequestHandler = new FormRequestHandler();
        request = TestRequestMaker.fromString(GETformRequest.ENTIRE_MESSAGE);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Response response = formRequestHandler.run(request);
        Assert.assertEquals(MIMETypes.HTML, response.getHeaders().getContentType());
        Assert.assertEquals(222, response.getHeaders().getContentLength());
        Assert.assertEquals(GETformResponse.BODY, response.getBody().getContent());
    }
}