package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETindexhtmlRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileRequestHandlerTest {
    private FileRequestHandler fileRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        fileRequestHandler = new FileRequestHandler();
        request = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Response response = fileRequestHandler.run(request);

    }
}