package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.rangehandler.GETrange0to12Request;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestHeaderTest {
    private RequestParser parser;
    private Map<String, String> headersMap;
    private RequestHeader header;

    @Before
    public void setUp() throws Exception {
        URL context = new URL("http://localhost:8080/");
        parser = new RequestParser(context);
        headersMap = parser.parseHeaders(GETHelloworldRequest.HEADERS);
        header = new RequestHeader(headersMap);
    }

    @Test
    public void testGetHost() {
        assertEquals("localhost", header.getHost());
    }

    @Test
    public void testGetConnection() {
        assertEquals("keep-alive", header.getConnection());
    }

    @Test
    public void testGetAccept() {
        String[] expected = {"text/html", "application/xhtml+xml", "application/xml;q=0.9", "image/webp", "*/*;q=0.8"};
        assertEquals(expected, header.getAccept());
    }

    @Test
    public void testGetUserAgent() {
        assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36",
                header.getUserAgent());
    }

    @Test
    public void testGetAcceptEncoding() {
        String[] expected = new String[0];
        assertEquals(expected, header.getAcceptEncoding());
    }

    @Test
    public void testGetAcceptLanguage() {
        String[] expected = {"en-US", "en;q=0.8"};
        assertEquals(expected, header.getAcceptLanguage());
    }

    @Test
    public void testGetContentLength() {
        assertEquals(0, header.getContentLength());
    }

    @Test
    public void testGetRange() {
        headersMap = parser.parseHeaders(GETrange0to12Request.HEADERS);
        header = new RequestHeader(headersMap);

        assertEquals("bytes=0-12", header.getRange());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(header.isEmpty());

        Map<String, String> emptyMap = new HashMap<String, String>();
        header = new RequestHeader(emptyMap);
        assertTrue(header.isEmpty());
    }
}