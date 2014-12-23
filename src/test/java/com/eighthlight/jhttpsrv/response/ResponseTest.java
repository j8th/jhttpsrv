package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResponseTest {
    private Response response;
    private ResponseHeader headers;
    private ResponseBody body;

    @Before
    public void setUp() throws Exception {
        response = new Response();
        headers = new ResponseHeader();
        body = new ResponseBody();
    }

    @Test
    public void testGetProtocolVersion() {
        Assert.assertEquals("1.1", response.getProtocolVersion());
    }

    @Test
    public void testSet_Get_StatusCode() {
        Assert.assertEquals(0, response.getStatusCode());
        response.setStatusCode(StatusCodes.OK);
        Assert.assertEquals(StatusCodes.OK, response.getStatusCode());
    }

    @Test
    public void testGetReasonPhrase() {
        Assert.assertEquals(null, response.getReasonPhrase());
        response.setStatusCode(StatusCodes.OK);
        Assert.assertEquals(StatusCodes.OK_PHRASE, response.getReasonPhrase());
    }

    @Test
    public void testSet_Get_Headers() {
        ResponseHeader initialHeader = response.getHeaders();
        Assert.assertNotNull(initialHeader);
        Assert.assertNotSame(headers, initialHeader);
        response.setHeaders(headers);
        Assert.assertEquals(headers, response.getHeaders());
    }

    @Test
    public void testSet_Get_Body() {
        ResponseBody initialBody = response.getBody();
        Assert.assertNotNull(initialBody);
        Assert.assertNotSame(body, response.getBody());
        response.setBody(body);
        Assert.assertEquals(body, response.getBody());
    }
}