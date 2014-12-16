package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class TimeRequestHandlerTest {
    private TimeRequestHandler timeRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        RequestParser parser = new RequestParser();

        timeRequestHandler = new TimeRequestHandler();
        request = GETRequestChrome.asObj();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Response response = timeRequestHandler.run(request);
        ZoneId zoneid = ZoneId.of("US/Central");
        ZonedDateTime zdt = ZonedDateTime.now(zoneid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz");
        String now = zdt.format(formatter);
        Assert.assertEquals(now, response.getBody().getContent());
    }
}