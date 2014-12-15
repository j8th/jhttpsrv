package com.eighthlight.jhttpsrv.parser;

import com.eighthlight.jhttpsrv.request.RequestHeader;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParserTest {
    private RequestParser myParser;

    @Before
    public void setUp() throws Exception {
        myParser = new RequestParser();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parseRequestLine() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Method", "GET");
        expected.put("URL", "/helloworld");
        expected.put("Protocol-Version", "1.1");

        Map<String, String> myMap = myParser.parseRequestLine(GETRequestChrome.REQUEST_LINE);

        Assert.assertEquals(expected, myMap);
    }

    @Test
    public void parseHeaders() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Host", "localhost");
        expected.put("Connection", "keep-alive");
        expected.put("Cache-Control", "max-age=0");
        expected.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        expected.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
        expected.put("Accept-Language", "en-US,en;q=0.8");

        Map<String, String> myMap = myParser.parseHeaders(GETRequestChrome.HEADERS);

        Assert.assertEquals(expected, myMap);
    }

    @Test
    public void parseInputStream() {
        InputStream myis = new ByteArrayInputStream(GETRequestChrome.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8));
        try {
            Request request = myParser.parseInputStream(myis);
            Assert.assertTrue(request.isGET());
            Assert.assertEquals("/helloworld", request.getURL());
            RequestHeader header = request.getHeader();
            Assert.assertEquals("localhost", header.getHost());
        } catch (IOException e) {
            Assert.fail("We should never get an exception here from a simple string-backed InputStream.  What did you do wrong?");
        }
    }
}