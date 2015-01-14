package com.eighthlight.jhttpsrv.parser;

import com.eighthlight.jhttpsrv.request.RequestHeader;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParserTest {
    private RequestParser myParser;

    @Before
    public void setUp() throws Exception {
        URL context = new URL("http://localhost:8080/");
        myParser = new RequestParser(context);
    }

    @Test
    public void testParseRequestLine() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Method", "GET");
        expected.put("URL", "/helloworld");
        expected.put("Protocol-Version", "1.1");

        Map<String, String> myMap = myParser.parseRequestLine(GETHelloworldRequest.REQUEST_LINE);

        Assert.assertEquals(expected, myMap);
    }

    @Test
    public void testParseRequestLineEmptyString() {
        Map<String, String> myMap = myParser.parseRequestLine("");

        Assert.assertTrue(myMap.isEmpty());
    }

    @Test
    public void testParseHeaders() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Host", "localhost");
        expected.put("Connection", "keep-alive");
        expected.put("Cache-Control", "max-age=0");
        expected.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        expected.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
        expected.put("Accept-Language", "en-US,en;q=0.8");

        Map<String, String> myMap = myParser.parseHeaders(GETHelloworldRequest.HEADERS);

        Assert.assertEquals(expected, myMap);
    }

    @Test
    public void testParseHeadersEmptyString() {
        Map<String, String> myMap = myParser.parseHeaders("");

        Assert.assertTrue(myMap.isEmpty());
    }

    @Test
    public void testParseInputStream() {
        InputStream myis = new ByteArrayInputStream(GETHelloworldRequest.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8));
        try {
            Request request = myParser.parseInputStream(myis);
            Assert.assertEquals(ProtocolStrings.HTTP_METHOD_GET, request.getMethod());
            Assert.assertEquals("/helloworld", request.getURLPath());
            RequestHeader header = request.getHeader();
            Assert.assertEquals("localhost", header.getHost());
        } catch (IOException e) {
            Assert.fail("We should never get an exception here from a simple string-backed InputStream.  What did you do wrong?");
        }
    }
}