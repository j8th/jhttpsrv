package com.eighthlight.jhttpsrv.request;

import com.eighthlight.jhttpsrv.testmessage.GETRequestChrome;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class BodyTest {
    private Body body;

    @Before
    public void setUp() throws Exception {
        body = new Body(GETRequestChrome.BODY);
    }

    @After
    public void tearDown() throws Exception {

    }
}