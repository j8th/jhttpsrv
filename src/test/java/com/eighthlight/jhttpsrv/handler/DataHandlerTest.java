package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import com.eighthlight.jhttpsrv.testmessage.chrome.datahandler.DELETEDataRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.datahandler.GETDataRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.datahandler.POSTDataRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.datahandler.PUTDataRequest;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class DataHandlerTest {
    private DataHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new DataHandler();
    }

    @Test
    public void testRun() throws Exception {
        Request GETrequest = TestRequestMaker.fromString(GETDataRequest.ENTIRE_MESSAGE);
        Request POSTrequest = TestRequestMaker.fromString(POSTDataRequest.ENTIRE_MESSAGE);
        Request PUTrequest = TestRequestMaker.fromString(PUTDataRequest.ENTIRE_MESSAGE);
        Request DELETErequest = TestRequestMaker.fromString(DELETEDataRequest.ENTIRE_MESSAGE);
        Response response;
        String content;

        response = handler.run(GETrequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());
        content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertFalse(content.contains("mykey=myval"));

        response = handler.run(POSTrequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());

        response = handler.run(GETrequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());
        content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertThat(content, containsString("mykey=myval"));

        response = handler.run(PUTrequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());

        response = handler.run(GETrequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());
        content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertThat(content, containsString("mykey=dude"));

        response = handler.run(DELETErequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());

        response = handler.run(GETrequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());
        content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertEquals("", content);
    }
}