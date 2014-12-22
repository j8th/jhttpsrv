package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestTest {
    private RequestParser parser;
    private Map<String, String> requestLine;
    private RequestHeader header;
    private RequestBody body;
    private Request request;

    @Before
    public void setUp() throws Exception {
        parser = new RequestParser();
        requestLine = parser.parseRequestLine(GETHelloworldRequest.REQUEST_LINE);
        header = new RequestHeader(parser.parseHeaders(GETHelloworldRequest.HEADERS));
        request = new Request(requestLine, header);
    }

    @Test
    public void getHeaders() {
        Assert.assertEquals(header, request.getHeader());
    }

    @Test
    public void isGET() {
        Assert.assertTrue(request.isGET());
    }

    @Test
    public void isPOST() {
        Assert.assertFalse(request.isPOST());
    }

    @Test
    public void getURL() {
        Assert.assertEquals("/helloworld", request.getURL());
    }

    @Test
    public void getMethod() {
        Assert.assertEquals(ProtocolStrings.HTTP_METHOD_GET, request.getMethod());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertFalse(request.isEmpty());

        request = new Request(new HashMap<String, String>(), new RequestHeader(new HashMap<String, String>()));
        Assert.assertTrue(request.isEmpty());
    }
}