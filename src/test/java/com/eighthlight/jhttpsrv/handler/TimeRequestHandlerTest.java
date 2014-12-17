package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeRequestHandlerTest {
    private TimeRequestHandler timeRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        RequestParser parser = new RequestParser();

        timeRequestHandler = new TimeRequestHandler();
        request = TestRequestMaker.fromString(GETHelloworldRequest.ENTIRE_MESSAGE);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Response response = timeRequestHandler.run(request);
        ZoneId zoneid = ZoneId.of("US/Central");
        ZonedDateTime zdt = ZonedDateTime.now(zoneid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm zzz");
        String now = zdt.format(formatter);
        Assert.assertEquals(now, response.getBody().getContent());
    }

    @Test
    public void testWaits() throws Exception {
        long before = System.currentTimeMillis();
        Response response = timeRequestHandler.run(request);
        long after = System.currentTimeMillis();
        long difference = after - before;

        // We allow some leeway, because waiting tends to be rather imprecise with computers.
        if(difference < 900 || difference > 1100)
            Assert.fail("The TimeRequestHandler did not wait one second.");
    }
}