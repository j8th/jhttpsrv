package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.OPTIONSRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class OptionsRequestHandlerTest {
    private Map<Pattern, String> options;
    private OptionsRequestHandler optionsRequestHandler;

    @Before
    public void setUp() throws Exception {
        options = new LinkedHashMap<Pattern, String>();
    }

    @Test
    public void testRun() throws Exception {
        options.put(Pattern.compile("/method_options"), ProtocolStrings.HTTP_METHOD_GET);
        options.put(Pattern.compile("/method_options"), ProtocolStrings.HTTP_METHOD_POST);
        options.put(Pattern.compile("/method_options"), ProtocolStrings.HTTP_METHOD_PUT);
        options.put(Pattern.compile("/method_options"), ProtocolStrings.HTTP_METHOD_HEAD);
        optionsRequestHandler = new OptionsRequestHandler(options);

        Request request = TestRequestMaker.fromString(OPTIONSRequest.ENTIRE_MESSAGE);
        Response response = optionsRequestHandler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        List<String> allowMethods = Arrays.asList(response.getHeaders().getAllow());
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_GET));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_POST));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_PUT));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_HEAD));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_OPTIONS));
        assertEquals(5, allowMethods.size());
    }

    @Test
    public void testRunNoOptions() throws Exception {
        optionsRequestHandler = new OptionsRequestHandler(options);

        Request request = TestRequestMaker.fromString(OPTIONSRequest.ENTIRE_MESSAGE);
        Response response = optionsRequestHandler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        List<String> allowMethods = Arrays.asList(response.getHeaders().getAllow());
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_OPTIONS));
        assertEquals(1, allowMethods.size());
    }

    @Test
    public void testRun_VerbOnlyAppearsOnce() throws Exception {
        options.put(Pattern.compile("/method_options"), ProtocolStrings.HTTP_METHOD_GET);
        options.put(Pattern.compile(".*"), ProtocolStrings.HTTP_METHOD_GET);
        optionsRequestHandler = new OptionsRequestHandler(options);

        Request request = TestRequestMaker.fromString(OPTIONSRequest.ENTIRE_MESSAGE);
        Response response = optionsRequestHandler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        List<String> allowMethods = Arrays.asList(response.getHeaders().getAllow());
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_GET));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_OPTIONS));
        assertEquals(2, allowMethods.size());
    }

    @Test
    public void testRun_RegexMatches() throws Exception {
        options.put(Pattern.compile(".*"), ProtocolStrings.HTTP_METHOD_GET);
        options.put(Pattern.compile(".*"), ProtocolStrings.HTTP_METHOD_POST);
        options.put(Pattern.compile("/method_options"), ProtocolStrings.HTTP_METHOD_HEAD);
        optionsRequestHandler = new OptionsRequestHandler(options);

        Request request = TestRequestMaker.fromString(OPTIONSRequest.ENTIRE_MESSAGE);
        Response response = optionsRequestHandler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        List<String> allowMethods = Arrays.asList(response.getHeaders().getAllow());
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_GET));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_POST));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_HEAD));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_OPTIONS));
        assertEquals(4, allowMethods.size());
    }

    @Test
    public void testRun_AllowHeaderDoesNotIncludingNonMatchingOptions() throws Exception {
        options.put(Pattern.compile(".*"), ProtocolStrings.HTTP_METHOD_GET);
        options.put(Pattern.compile("/form"), ProtocolStrings.HTTP_METHOD_POST);
        options.put(Pattern.compile("/method_options"), ProtocolStrings.HTTP_METHOD_HEAD);
        options.put(Pattern.compile("/patch-content.txt"), ProtocolStrings.HTTP_METHOD_PATCH);
        optionsRequestHandler = new OptionsRequestHandler(options);

        Request request = TestRequestMaker.fromString(OPTIONSRequest.ENTIRE_MESSAGE);
        Response response = optionsRequestHandler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        List<String> allowMethods = Arrays.asList(response.getHeaders().getAllow());
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_GET));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_HEAD));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_OPTIONS));
        assertEquals(3, allowMethods.size());
    }
}