package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.shared.StatusCodes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResponseTest {
    private Response response;

    @Before
    public void setUp() throws Exception {
        response = new Response();
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
}