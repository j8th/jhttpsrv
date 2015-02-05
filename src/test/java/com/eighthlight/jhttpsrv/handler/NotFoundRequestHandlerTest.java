package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETindexhtmlRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotFoundRequestHandlerTest {
    private NotFoundRequestHandler notFoundRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        notFoundRequestHandler = new NotFoundRequestHandler();
        request = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);
    }

    @Test
    public void testRun() {
        Response response = notFoundRequestHandler.run(request);

        assertEquals(StatusCodes.NOT_FOUND, response.getStatusCode());
    }
}