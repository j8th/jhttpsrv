package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
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
    public void testGetHeaders() {
        Assert.assertEquals(header, request.getHeader());
    }

    @Test
    public void testGetURL() {
        Assert.assertEquals("/helloworld", request.getURL());
    }

    @Test
    public void testGetMethod() {
        Assert.assertEquals(ProtocolStrings.HTTP_METHOD_GET, request.getMethod());
    }

    @Test
    public void testIsValid() {
        Assert.assertTrue(request.isValid());
    }

    @Test
    public void testEmptyRequestIsInvalid() {
        request = new Request(new HashMap<String, String>(), new RequestHeader(new HashMap<String, String>()));
        Assert.assertFalse(request.isValid());
    }

    @Test
    public void testGarbageRequestIsInvalid() {
        Map<String, String> requestLine = new HashMap<String, String>();
        requestLine.put(ProtocolStrings.METHOD, "laknfe");
        requestLine.put(ProtocolStrings.URL, "ekkk");
        requestLine.put(ProtocolStrings.PROTOCOL_VERSION, "HTTP/1.1");

        request = new Request(requestLine, new RequestHeader(new HashMap<String, String>()));
        Assert.assertFalse(request.isValid());
    }
}