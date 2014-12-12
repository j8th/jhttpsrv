package com.eighthlight.jhttpsrv.response;

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
        Assert.assertNull(body.getContent());
        body.setContent("<p>hello</p>\n");
        Assert.assertEquals("<p>hello</p>\n", body.getContent());
    }
}