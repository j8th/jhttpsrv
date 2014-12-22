package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class ResponseBodyTest {
    private ResponseBody body;

    @Before
    public void setUp() throws Exception {
        body = new ResponseBody();
    }

    @Test
    public void set_get_Content_String() {
        String content = "<p>hello</p>";

        Assert.assertArrayEquals(new byte[0], body.getContent());
        body.setContent(content);
        Assert.assertArrayEquals(content.getBytes(StandardCharsets.UTF_8), body.getContent());
    }

    @Test
    public void testSet_Get_ContentBytes() {
        byte[] content = "<p>goodbye</p>".getBytes(StandardCharsets.UTF_8);

        Assert.assertArrayEquals(new byte[0], body.getContent());
        body.setContent(content);
        Assert.assertArrayEquals(content, body.getContent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullContentBytes() {
        byte[] bad = null;
        body.setContent(bad);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullContentString() {
        String bad = null;
        body.setContent(bad);
    }

    @Test
    public void getContentLength() {
        Assert.assertEquals(0, body.getContentLength());
        body.setContent(GETHelloworldResponse.BODY);
        Assert.assertEquals(93, body.getContentLength());
    }
}