package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.testmessage.chrome.*;
import com.eighthlight.jhttpsrv.testmessage.chrome.datahandler.GETDataRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.datahandler.POSTDataRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RouteTest {
    private Route route;
    private Request request;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testMatches() throws Exception {
        route = new Route(ProtocolStrings.HTTP_METHOD_GET, Pattern.compile(Pattern.quote("/index.html")));
        request = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);

        assertTrue(route.matches(request));
    }

    @Test
    public void testMatches_VerbDoesNotMatch() throws Exception {
        route = new Route(ProtocolStrings.HTTP_METHOD_POST, Pattern.compile("/restful"));
        request = TestRequestMaker.fromString(GETDataRequest.ENTIRE_MESSAGE);

        assertFalse(route.matches(request));
    }

    @Test
    public void testMatches_UrlDoesNotMatch() throws Exception {
        route = new Route(ProtocolStrings.HTTP_METHOD_GET, Pattern.compile(Pattern.quote("/index.html")));
        request = TestRequestMaker.fromString(GETDataRequest.ENTIRE_MESSAGE);

        assertFalse(route.matches(request));
    }

    @Test
    public void  testMatches_AnyUrlRegex() throws Exception {
        route = new Route(ProtocolStrings.HTTP_METHOD_GET, Pattern.compile(".*"));
        Request matchingRequest1 = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);
        Request matchingRequest2 = TestRequestMaker.fromString(GETDataRequest.ENTIRE_MESSAGE);
        Request notMatchingRequest1 = TestRequestMaker.fromString(POSTDataRequest.ENTIRE_MESSAGE);
        Request notMatchingRequest2 = TestRequestMaker.fromString(PUTtextfileRequest.ENTIRE_MESSAGE);

        assertTrue(route.matches(matchingRequest1));
        assertTrue(route.matches(matchingRequest2));
        assertFalse(route.matches(notMatchingRequest1));
        assertFalse(route.matches(notMatchingRequest2));
    }
}