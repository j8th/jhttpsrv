package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseHeaderTest {
    private ResponseHeader responseHeader;

    @Before
    public void setUp() throws Exception {
        responseHeader = new ResponseHeader();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void set_get_ContentType() {
        Assert.assertEquals("", responseHeader.getContentType());
        responseHeader.setContentType(MIMETypes.HTML);
        Assert.assertEquals(MIMETypes.HTML, responseHeader.getContentType());
    }

    @Test
    public void set_get_ContentLength() {
        Assert.assertEquals(0, responseHeader.getContentLength());
        responseHeader.setContentLength(412);
        Assert.assertEquals(412, responseHeader.getContentLength());
    }

    @Test
    public void set_get_Location() {
        Assert.assertEquals("", responseHeader.getLocation());
        responseHeader.setLocation("http://example.com/redirect/here");
        Assert.assertEquals("http://example.com/redirect/here", responseHeader.getLocation());
    }
}