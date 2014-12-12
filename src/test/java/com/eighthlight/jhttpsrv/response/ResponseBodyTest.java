package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.testmessage.chrome.GETResponseChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseBodyTest {
    private ResponseBody body;

    @Before
    public void setUp() throws Exception {
        body = new ResponseBody();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void set_get_Content() {
        Assert.assertEquals("", body.getContent());
        body.setContent("<p>hello</p>\n");
        Assert.assertEquals("<p>hello</p>\n", body.getContent());
    }

    @Test
    public void getContentLength() {
        Assert.assertEquals(0, body.getContentLength());
        body.setContent(GETResponseChrome.BODY);
        Assert.assertEquals(93, body.getContentLength());
    }
}