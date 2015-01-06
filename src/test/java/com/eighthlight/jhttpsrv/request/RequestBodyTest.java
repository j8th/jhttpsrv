package com.eighthlight.jhttpsrv.request;

import org.junit.Assert;
import org.junit.Test;

public class RequestBodyTest {
    private RequestBody requestBody;

    @Test
    public void testIsEmpty() {
        requestBody = new RequestBody("");
        Assert.assertTrue(requestBody.isEmpty());
    }

    @Test
    public void testGetContent() {
        requestBody = new RequestBody("The content\n");
        Assert.assertEquals("The content\n", requestBody.getContent());
    }
}