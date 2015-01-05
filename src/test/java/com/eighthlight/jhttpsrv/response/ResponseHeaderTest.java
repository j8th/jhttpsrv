package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.constants.MIMETypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResponseHeaderTest {
    private ResponseHeader responseHeader;

    @Before
    public void setUp() throws Exception {
        responseHeader = new ResponseHeader();
    }

    @Test
    public void testSet_Get_ContentType() {
        Assert.assertEquals("", responseHeader.getContentType());
        responseHeader.setContentType(MIMETypes.HTML);
        Assert.assertEquals(MIMETypes.HTML, responseHeader.getContentType());
    }

    @Test
    public void testSet_Get_ContentLength() {
        Assert.assertEquals(0, responseHeader.getContentLength());
        responseHeader.setContentLength(412);
        Assert.assertEquals(412, responseHeader.getContentLength());
    }

    @Test
    public void testSet_Get_Location() {
        Assert.assertEquals("", responseHeader.getLocation());
        responseHeader.setLocation("http://example.com/redirect/here");
        Assert.assertEquals("http://example.com/redirect/here", responseHeader.getLocation());
    }
}