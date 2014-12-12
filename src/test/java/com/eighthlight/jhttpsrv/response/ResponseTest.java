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


        //headers.setContentType(MIMETypes.HTML);
        //headers.setContentLength();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getProtocolVersion() {
        Assert.assertEquals("1.1", response.getProtocolVersion());
    }

    @Test
    public void set_get_StatusCode() {
        Assert.assertEquals(0, response.getStatusCode());
        response.setStatusCode(StatusCodes.OK);
        Assert.assertEquals(StatusCodes.OK, response.getStatusCode());
    }

    @Test
    public void getReasonPhrase() {
        Assert.assertEquals(null, response.getReasonPhrase());
        response.setStatusCode(StatusCodes.OK);
        Assert.assertEquals(StatusCodes.OK_PHRASE, response.getReasonPhrase());
    }

    @Test
    public void set_get_Headers() {
        Assert.assertNull(response.getHeaders());
        response.setHeaders(headers);
        Assert.assertEquals(headers, response.getHeaders());
    }

    public void set_get_Body() {
        Assert.assertNull(response.getBody());
        response.setBody(body);
        Assert.assertEquals(body, response.getBody());
    }
}