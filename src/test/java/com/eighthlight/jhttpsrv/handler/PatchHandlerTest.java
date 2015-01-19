package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import com.eighthlight.jhttpsrv.testmessage.chrome.patchhandler.GETpatchtestRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.patchhandler.PATCHdefaultRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.patchhandler.PATCHupdateRequest;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class PatchHandlerTest {
    private PatchHandler handler;
    private FileRequestHandler fileRequestHandler;

    @Before
    public void setUp() throws Exception {
        fileRequestHandler = new FileRequestHandler(System.getProperty("user.dir") + "/www");
        handler = new PatchHandler(fileRequestHandler);
    }

    @Test
    public void testRun() throws Exception {
        Request GETRequest = TestRequestMaker.fromString(GETpatchtestRequest.ENTIRE_MESSAGE);
        Request PATCHUpdateRequest = TestRequestMaker.fromString(PATCHupdateRequest.ENTIRE_MESSAGE);
        Request PATCHDefaultRequest = TestRequestMaker.fromString(PATCHdefaultRequest.ENTIRE_MESSAGE);
        Response response;
        String content;

        response = handler.run(GETRequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());
        content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertEquals("default content\n", content);

        response = handler.run(PATCHUpdateRequest);
        assertEquals(StatusCodes.NO_CONTENT, response.getStatusCode());

        response = handler.run(GETRequest);
        assertEquals(StatusCodes.OK, response.getStatusCode());
        content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertEquals("patch content\n", content);

        handler.run(PATCHDefaultRequest);
        response = handler.run(GETRequest);
        content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertEquals("default content\n", content);
    }
}