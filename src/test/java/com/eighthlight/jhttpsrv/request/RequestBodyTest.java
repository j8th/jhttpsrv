package com.eighthlight.jhttpsrv.request;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestBodyTest {
    private RequestBody requestBody;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsEmpty() {
        requestBody = new RequestBody("");
        Assert.assertTrue(requestBody.isEmpty());
    }

}