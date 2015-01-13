package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.constants.MIMETypes;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import static org.junit.Assert.*;
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
        assertArrayEquals(statusLineBytes, responseBuilder.buildStatusLine(response));
    }

    @Test
    public void testBuildHeaders() {
        byte[] headerBytes = GETHelloworldResponse.HEADERS.getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(headerBytes, responseBuilder.buildHeaders(response.getHeaders()));
    }

    @Test
    public void testBuildHeadersWithEmptyHeaders() {
        response.setHeaders(new ResponseHeader());
        assertArrayEquals(new byte[0], responseBuilder.buildHeaders(response.getHeaders()));
    }

    @Test
    public void testBuildHeadersWithAllow() {
        ResponseHeader header = new ResponseHeader();
        header.setAllow(new String[] {ProtocolStrings.HTTP_METHOD_GET, ProtocolStrings.HTTP_METHOD_POST});
        byte[] headerBytes = "Allow: GET,POST\r\n".getBytes(StandardCharsets.UTF_8);

        assertArrayEquals(headerBytes, responseBuilder.buildHeaders(header));
    }

    @Test
    public void testBuildBody() {
        byte[] bodyBytes = GETHelloworldResponse.BODY.getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(bodyBytes, responseBuilder.buildBody(response.getBody()));
    }

    @Test
    public void testBuildResponse() {
        byte[] responseBytes = GETHelloworldResponse.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(responseBytes, responseBuilder.buildResponse(response));
    }


}