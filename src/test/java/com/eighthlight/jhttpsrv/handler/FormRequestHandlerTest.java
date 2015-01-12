package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.POSTFormRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.PUTFormRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class FormRequestHandlerTest {
    private FormRequestHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new FormRequestHandler();
    }

    @Test
    public void testRun() throws Exception {
        Request request = TestRequestMaker.fromString(POSTFormRequest.ENTIRE_MESSAGE);
        Response response = handler.run(request);

        String content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        assertThat(content, containsString("mykey"));
        assertThat(content, containsString("myval"));
        assertThat(content, containsString("weather"));
        assertThat(content, containsString("cloudy"));
        assertThat(content, containsString("city"));
        assertThat(content, containsString("Chicago"));
    }

    @Test
    public void testPUTRequest() throws Exception {
        Request request = TestRequestMaker.fromString(PUTFormRequest.ENTIRE_MESSAGE);
        Response response = handler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
    }
}