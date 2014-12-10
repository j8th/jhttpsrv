package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.MessageParser;
import com.eighthlight.jhttpsrv.testmessage.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class RequestTest {
    private MessageParser parser;
    private Map<String, String> requestLine;
    private Header header;
    private Body body;
    private Request request;

    @Before
    public void setUp() throws Exception {
        parser = new MessageParser();
        requestLine = parser.parseRequestLine(GETRequestChrome.REQUEST_LINE);
        header = new Header(parser.parseHeaders(GETRequestChrome.HEADERS));
        body = new Body(GETRequestChrome.BODY);
        request = new Request(requestLine, header, body);
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
}