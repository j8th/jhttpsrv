package com.eighthlight.jhttpsrv.request;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestBodyTest {
    private RequestBody requestBody;

    @Test
    public void testIsEmpty() {
        requestBody = new RequestBody("");
        Assert.assertTrue(requestBody.isEmpty());
    }
}