package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETResponseChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        body.setContent(GETResponseChrome.BODY);
        header.setContentType(MIMETypes.HTML);
        header.setContentLength(body.getContentLength());

        response.setHeaders(header);
        response.setBody(body);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void buildStatusLine() {
        Assert.assertEquals(GETResponseChrome.STATUS_LINE, responseBuilder.buildStatusLine(response));
    }

    @Test
    public void buildHeaders() {
        Assert.assertEquals(GETResponseChrome.HEADERS, responseBuilder.buildHeaders(response));
    }

    @Test
    public void buildBody() {
        Assert.assertEquals(GETResponseChrome.BODY, responseBuilder.buildBody(response));
    }

    @Test
    public void buildResponse() {
        Assert.assertEquals(GETResponseChrome.ENTIRE_MESSAGE, responseBuilder.buildResponse(response));
    }
}