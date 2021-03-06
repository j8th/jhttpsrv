package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeRequestHandlerTest {
    private TimeRequestHandler timeRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        request = TestRequestMaker.fromString(GETHelloworldRequest.ENTIRE_MESSAGE);
    }

    @Test
    public void testRun() throws Exception {
        timeRequestHandler = new TimeRequestHandler(0);
        Response response = timeRequestHandler.run(request);
        ZoneId zoneid = ZoneId.of("US/Central");
        ZonedDateTime zdt = ZonedDateTime.now(zoneid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm zzz");
        String now = zdt.format(formatter);
        String body = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertEquals(now, body);
    }

    @Test
    public void testWaitsTheIndicatedMilliseconds() throws Exception {
        timeRequestHandler = new TimeRequestHandler(200);
        long before = System.currentTimeMillis();
        timeRequestHandler.run(request);
        long after = System.currentTimeMillis();
        long difference = after - before;

        if(difference < 100 || difference > 300)
            fail("The TimeRequestHandler did not wait roughly the allotted time (200 milliseconds).");
    }
}