package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class ResponseBuilderTest {
    private Response response;
    private ResponseHeader header;
    private ResponseBody body;
    private ResponseBuilder responseBuilder;

    @Before
    public void setUp() throws Exception {
        response = new Response();
        header = new ResponseHeader();
        body = new ResponseBody();
        responseBuilder = new ResponseBuilder();

        response.setStatusCode(StatusCodes.OK);
        body.setContent(GETHelloworldResponse.BODY);
        header.setContentType(MIMETypes.HTML);
        header.setContentLength(body.getContentLength());

        response.setHeaders(header);
        response.setBody(body);
    }

    @Test
    public void testBuildStatusLine() {
        byte[] statusLineBytes = GETHelloworldResponse.STATUS_LINE.getBytes(StandardCharsets.UTF_8);
        Assert.assertArrayEquals(statusLineBytes, responseBuilder.buildStatusLine(response));
    }

    @Test
    public void testBuildHeaders() {
        byte[] headerBytes = GETHelloworldResponse.HEADERS.getBytes(StandardCharsets.UTF_8);
        Assert.assertArrayEquals(headerBytes, responseBuilder.buildHeaders(response));
    }

    @Test
    public void testBuildHeadersWithEmptyHeaders() {
        response.setHeaders(new ResponseHeader());
        Assert.assertArrayEquals(new byte[0], responseBuilder.buildHeaders(response));
    }

    @Test
    public void testBuildBody() {
        byte[] bodyBytes = GETHelloworldResponse.BODY.getBytes(StandardCharsets.UTF_8);
        Assert.assertArrayEquals(bodyBytes, responseBuilder.buildBody(response));
    }

    @Test
    public void testBuildResponse() {
        byte[] responseBytes = GETHelloworldResponse.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8);
        Assert.assertArrayEquals(responseBytes, responseBuilder.buildResponse(response));
    }
}