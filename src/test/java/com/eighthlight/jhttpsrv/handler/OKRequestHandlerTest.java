package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.constants.MIMETypes;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class OKRequestHandlerTest {
    private OKRequestHandler okRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        request = TestRequestMaker.fromString(GETHelloworldRequest.ENTIRE_MESSAGE);
        okRequestHandler = new OKRequestHandler();
    }

    @Test
    public void testRun() {
        Response response = okRequestHandler.run(request);
        Assert.assertEquals(StatusCodes.OK, response.getStatusCode());
        Assert.assertEquals(MIMETypes.HTML, response.getHeaders().getContentType());
        Assert.assertArrayEquals(new byte[0], response.getBody().getContent());
    }
}