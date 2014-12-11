package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class HeaderTest {
    private Map<String, String> myHeadersMap;
    private Header myHeader;

    @Before
    public void setUp() throws Exception {
        RequestParser myParser = new RequestParser();
        myHeadersMap = myParser.parseHeaders(GETRequestChrome.HEADERS);
        myHeader = new Header(myHeadersMap);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getHost() {
        Assert.assertEquals("localhost", myHeader.getHost());
    }

    @Test
    public void getConnection() {
        Assert.assertEquals("keep-alive", myHeader.getConnection());
    }

    @Test
    public void getAccept() {
        String[] expected = {"text/html", "application/xhtml+xml", "application/xml;q=0.9", "image/webp", "*/*;q=0.8"};
        Assert.assertEquals(expected, myHeader.getAccept());
    }

    @Test
    public void getUserAgent() {
        Assert.assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36",
                myHeader.getUserAgent());
    }

    @Test
    public void getAcceptEncoding() {
        String[] expected = null;
        Assert.assertEquals(expected, myHeader.getAcceptEncoding());
    }

    @Test
    public void getAcceptLanguage() {
        String[] expected = {"en-US", "en;q=0.8"};
        Assert.assertEquals(expected, myHeader.getAcceptLanguage());
    }
}