package com.eighthlight.jhttpsrv.parsers;

import com.eighthlight.jhttpsrv.testmessages.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpMessageParserTest {
    private HttpMessageParser myParser;

    @Before
    public void setUp() throws Exception {
        myParser = new HttpMessageParser();
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

        Map<String, String> myMap = myParser.parseRequestLine(GETRequestChrome.RequestLine);

        Assert.assertEquals(expected, myMap);
    }
}