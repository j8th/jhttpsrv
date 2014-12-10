package com.eighthlight.jhttpsrv.parser;

import com.eighthlight.jhttpsrv.testmessage.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MessageParserTest {
    private MessageParser myParser;

    @Before
    public void setUp() throws Exception {
        myParser = new MessageParser();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parseRequestLine() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Method", "GET");
        expected.put("URL", "/something/cool/here");
        expected.put("Protocol-Version", "1.1");

        Map<String, String> myMap = myParser.parseRequestLine(GETRequestChrome.REQUEST_LINE);

        Assert.assertEquals(expected, myMap);
    }

    @Test
    public void parseHeaders() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Host", "localhost");
        expected.put("Connection", "keep-alive");
        expected.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        expected.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
        expected.put("Accept-Encoding", "gzip, deflate, sdch");
        expected.put("Accept-Language", "en-US,en;q=0.8");

        Map<String, String> myMap = myParser.parseHeaders(GETRequestChrome.HEADERS);

        Assert.assertEquals(expected, myMap);

    }
}