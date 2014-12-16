package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        requestLine = parser.parseRequestLine(GETRequestChrome.REQUEST_LINE);
        header = new RequestHeader(parser.parseHeaders(GETRequestChrome.HEADERS));
        request = new Request(requestLine, header);
    }

    @After
    public void tearDown() throws Exception {

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
}