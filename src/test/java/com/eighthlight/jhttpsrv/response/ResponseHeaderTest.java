package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.constants.MIMETypes;
import static org.junit.Assert.*;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
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
        assertEquals("", responseHeader.getContentType());
        responseHeader.setContentType(MIMETypes.HTML);
        assertEquals(MIMETypes.HTML, responseHeader.getContentType());
    }

    @Test
    public void testSet_Get_ContentLength() {
        assertEquals(0, responseHeader.getContentLength());
        responseHeader.setContentLength(412);
        assertEquals(412, responseHeader.getContentLength());
    }

    @Test
    public void testSet_Get_Location() {
        assertEquals("", responseHeader.getLocation());
        responseHeader.setLocation("http://example.com/redirect/here");
        assertEquals("http://example.com/redirect/here", responseHeader.getLocation());
    }

    @Test
    public void testSet_Get_Allow() {
        assertArrayEquals(new String[0], responseHeader.getAllow());
        String[] allowMethods = {ProtocolStrings.HTTP_METHOD_GET, ProtocolStrings.HTTP_METHOD_POST};
        responseHeader.setAllow(allowMethods);
        assertArrayEquals(allowMethods, responseHeader.getAllow());
    }
}