package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.testmessage.chrome.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
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
        URL context = new URL("http://localhost:8080");
        parser = new RequestParser(context);
        requestLine = parser.parseRequestLine(GETHelloworldRequest.REQUEST_LINE);
        String method = requestLine.get(ProtocolStrings.METHOD);
        URL url = new URL(context, requestLine.get(ProtocolStrings.URL));
        header = new RequestHeader(parser.parseHeaders(GETHelloworldRequest.HEADERS));
        body = new RequestBody("");
        request = new Request(method, url, header, body);
    }

    @Test
    public void testGetHeaders() {
        assertEquals(header, request.getHeader());
    }

    @Test
    public void testGetBody() {
        assertEquals(body, request.getBody());
    }

    @Test
    public void testGetURL() {
        assertEquals("/helloworld", request.getURLPath());
    }

    @Test
    public void testGetURLPathPart() throws Exception {
        Request request = TestRequestMaker.fromString(GETparamsRequest.ENTIRE_MESSAGE);
        assertEquals("/parameters", request.getURLPath());
    }

    @Test
    public void testGetURLQueryPart() throws Exception {
        Request request = TestRequestMaker.fromString(GETparamsRequest.ENTIRE_MESSAGE);
        assertEquals("variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff",
                request.getURLQuery());
    }

    @Test
    public void testGetRequestLine() throws Exception {
        Request request = TestRequestMaker.fromString(GETDirectoryRequest.ENTIRE_MESSAGE);
        assertEquals("GET /dirtest HTTP/1.1", request.getRequestLine());
    }

    @Test
    public void testGetMethod() {
        assertEquals(ProtocolStrings.HTTP_METHOD_GET, request.getMethod());
    }

    @Test
    public void testGETRequestIsValid() {
        assertTrue(request.isValid());
    }

    @Test
    public void testPOSTRequestIsValid() throws Exception {
        Request request = TestRequestMaker.fromString(POSTFormRequest.ENTIRE_MESSAGE);

        assertTrue(request.isValid());
    }

    @Test
    public void testPUTRequestIsValid() throws Exception {
        Request request = TestRequestMaker.fromString(PUTtextfileRequest.ENTIRE_MESSAGE);

        assertTrue(request.isValid());
    }

    @Test
    public void testEmptyRequestIsInvalid() {
        request = new Request("", null, new RequestHeader(new HashMap<String, String>()), new RequestBody(""));
        assertFalse(request.isValid());
    }

    @Test
    public void testGarbageRequestIsInvalid() {
        request = new Request("laknfe", null, new RequestHeader(new HashMap<String, String>()), new RequestBody(""));
        assertFalse(request.isValid());
    }
}