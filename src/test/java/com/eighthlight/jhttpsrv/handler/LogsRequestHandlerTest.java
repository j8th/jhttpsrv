package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.logger.MemoryLogger;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import com.eighthlight.jhttpsrv.testmessage.chrome.basicauthhandler.GETlogsWithoutAuthRequest;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class LogsRequestHandlerTest {
    private LogsRequestHandler logsRequestHandler;
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = new MemoryLogger();
        logsRequestHandler = new LogsRequestHandler(logger);
    }

    @Test
    public void testRun() throws Exception {
        logger.log("Test message");
        logger.log("GET /some/url HTTP/1.1");
        logger.log("Simmons");
        Request request = TestRequestMaker.fromString(GETlogsWithoutAuthRequest.ENTIRE_MESSAGE);
        Response response = logsRequestHandler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        String content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertThat(content, containsString("Test message"));
        assertThat(content, containsString("GET /some/url HTTP/1.1"));
        assertThat(content, containsString("Simmons"));
    }
}