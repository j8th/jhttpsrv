package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestHeaderTest {
    private Map<String, String> myHeadersMap;
    private RequestHeader myHeader;

    @Before
    public void setUp() throws Exception {
        URL context = new URL("http://localhost:8080/");
        RequestParser myParser = new RequestParser(context);
        myHeadersMap = myParser.parseHeaders(GETHelloworldRequest.HEADERS);
        myHeader = new RequestHeader(myHeadersMap);
    }

    @Test
    public void testGetHost() {
        Assert.assertEquals("localhost", myHeader.getHost());
    }

    @Test
    public void testGetConnection() {
        Assert.assertEquals("keep-alive", myHeader.getConnection());
    }

    @Test
    public void testGetAccept() {
        String[] expected = {"text/html", "application/xhtml+xml", "application/xml;q=0.9", "image/webp", "*/*;q=0.8"};
        Assert.assertEquals(expected, myHeader.getAccept());
    }

    @Test
    public void testGetUserAgent() {
        Assert.assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36",
                myHeader.getUserAgent());
    }

    @Test
    public void testGetAcceptEncoding() {
        String[] expected = new String[0];
        Assert.assertEquals(expected, myHeader.getAcceptEncoding());
    }

    @Test
    public void testGetAcceptLanguage() {
        String[] expected = {"en-US", "en;q=0.8"};
        Assert.assertEquals(expected, myHeader.getAcceptLanguage());
    }

    @Test
    public void testGetContentLength() {
        Assert.assertEquals(0, myHeader.getContentLength());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertFalse(myHeader.isEmpty());

        Map<String, String> emptyMap = new HashMap<String, String>();
        myHeader = new RequestHeader(emptyMap);
        Assert.assertTrue(myHeader.isEmpty());
    }
}