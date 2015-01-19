package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GET404Request;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import com.eighthlight.jhttpsrv.testmessage.chrome.rangehandler.GETrange0to12Request;
import com.eighthlight.jhttpsrv.testmessage.chrome.rangehandler.GETrange12toBlankRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.rangehandler.GETrangeBlankto12Request;
import com.eighthlight.jhttpsrv.util.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RangeHandlerTest {
    private RangeHandler rangeHandler;
    private FileRequestHandler fileRequestHandler;

    @Before
    public void setUp() throws Exception {
        fileRequestHandler = new FileRequestHandler(System.getProperty("user.dir") + "/www");
        rangeHandler = new RangeHandler(fileRequestHandler);
    }

    @Test
    public void testRunRange0to12() throws Exception {
        Request request = TestRequestMaker.fromString(GETrange0to12Request.ENTIRE_MESSAGE);
        Response fileRequestHandlerResponse = fileRequestHandler.run(request);
        Response rangeHandlerResponse = rangeHandler.run(request);

        assertEquals(StatusCodes.OK, fileRequestHandlerResponse.getStatusCode());
        assertEquals(StatusCodes.PARTIAL_CONTENT, rangeHandlerResponse.getStatusCode());
        byte[] expected = new byte[13];
        System.arraycopy(fileRequestHandlerResponse.getBody().getContent(), 0, expected, 0, 13);
        assertArrayEquals(expected, rangeHandlerResponse.getBody().getContent());
    }

    @Test
    public void testRunRangeBlankto12() throws Exception {
        Request request = TestRequestMaker.fromString(GETrangeBlankto12Request.ENTIRE_MESSAGE);
        Response fileRequestHandlerResponse = fileRequestHandler.run(request);
        Response rangeHandlerResponse = rangeHandler.run(request);

        assertEquals(StatusCodes.OK, fileRequestHandlerResponse.getStatusCode());
        assertEquals(StatusCodes.PARTIAL_CONTENT, rangeHandlerResponse.getStatusCode());
        byte[] expected = ArrayUtils.getArrayTail(fileRequestHandlerResponse.getBody().getContent(), 12);
        assertArrayEquals(expected, rangeHandlerResponse.getBody().getContent());
    }

    @Test
    public void testRunRange12toBlank() throws Exception {
        Request request = TestRequestMaker.fromString(GETrange12toBlankRequest.ENTIRE_MESSAGE);
        Response fileRequestHandlerResponse = fileRequestHandler.run(request);
        Response rangeHandlerResponse = rangeHandler.run(request);

        assertEquals(StatusCodes.OK, fileRequestHandlerResponse.getStatusCode());
        assertEquals(StatusCodes.PARTIAL_CONTENT, rangeHandlerResponse.getStatusCode());
        byte[] expected = ArrayUtils.getArrayRange(fileRequestHandlerResponse.getBody().getContent(), 12, null);
        assertArrayEquals(expected, rangeHandlerResponse.getBody().getContent());
    }

    @Test
    public void testRunNonExistentFileStillThrows404() throws Exception {
        Request request = TestRequestMaker.fromString(GET404Request.ENTIRE_MESSAGE);
        Response response = rangeHandler.run(request);

        assertEquals(StatusCodes.NOT_FOUND, response.getStatusCode());
    }
}