package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.OPTIONSRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OptionsRequestHandlerTest {
    private OptionsRequestHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new OptionsRequestHandler();
    }

    @Test
    public void testRun() throws Exception {
        Request request = TestRequestMaker.fromString(OPTIONSRequest.ENTIRE_MESSAGE);
        Response response = handler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        List<String> allowMethods = Arrays.asList(response.getHeaders().getAllow());
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_GET));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_POST));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_PUT));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_HEAD));
        assertTrue(allowMethods.contains(ProtocolStrings.HTTP_METHOD_OPTIONS));
    }
}