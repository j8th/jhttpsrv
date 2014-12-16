package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class OKRequestHandlerTest {
    private OKRequestHandler okRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        RequestParser parser = new RequestParser();
        InputStream is = new ByteArrayInputStream(GETRequestChrome.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8));

        request = parser.parseInputStream(is);
        okRequestHandler = new OKRequestHandler();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void run() {
        Response response = okRequestHandler.run(request);
        Assert.assertEquals(StatusCodes.OK, response.getStatusCode());
        Assert.assertEquals(MIMETypes.HTML, response.getHeaders().getContentType());
        Assert.assertEquals("", response.getBody().getContent());
    }
}