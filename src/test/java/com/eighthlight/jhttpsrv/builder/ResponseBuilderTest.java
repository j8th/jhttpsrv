package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETResponseChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseBuilderTest {
    private Response response;
    private ResponseBuilder responseBuilder;

    @Before
    public void setUp() throws Exception {
        response = new Response();
        responseBuilder = new ResponseBuilder();

        response.setStatusCode(StatusCodes.OK);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void buildStatusLine() {
        Assert.assertEquals(GETResponseChrome.STATUS_LINE, responseBuilder.buildStatusLine(response));
    }

    @Test
    public void buildResponse() {

    }
}