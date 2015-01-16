package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import com.eighthlight.jhttpsrv.testmessage.chrome.basicauthhandler.GETlogsWithAuthRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.basicauthhandler.GETlogsWithWrongAuthRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.basicauthhandler.GETlogsWithoutAuthRequest;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class BasicAuthHandlerTest {
    private BasicAuthHandler basicAuthHandler;

    @Before
    public void setUp() throws Exception {
        basicAuthHandler = new BasicAuthHandler(new HelloWorldRequestHandler(), "Aladdin", "open sesame");
    }

    @Test
    public void testRunWithCredentials() throws Exception {
        Request request = TestRequestMaker.fromString(GETlogsWithAuthRequest.ENTIRE_MESSAGE);
        Response response = basicAuthHandler.run(request);
        String content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        assertThat(content, containsString("Hello World"));
        assertFalse(content.contains("Authentication required"));
    }

    @Test
    public void testRunWithoutCredentials() throws Exception {
        Request request = TestRequestMaker.fromString(GETlogsWithoutAuthRequest.ENTIRE_MESSAGE);
        Response response = basicAuthHandler.run(request);
        String content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);

        assertEquals(StatusCodes.UNAUTHORIZED, response.getStatusCode());
        assertThat(content, containsString("Authentication required"));
        assertFalse(content.contains("Hello World"));
    }

    @Test
    public void testRunWithWrongCredentials() throws Exception {
        Request request = TestRequestMaker.fromString(GETlogsWithWrongAuthRequest.ENTIRE_MESSAGE);
        Response response = basicAuthHandler.run(request);
        String content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);

        assertEquals(StatusCodes.UNAUTHORIZED, response.getStatusCode());
        assertThat(content, containsString("Authentication required"));
        assertFalse(content.contains("Hello World"));
    }
}