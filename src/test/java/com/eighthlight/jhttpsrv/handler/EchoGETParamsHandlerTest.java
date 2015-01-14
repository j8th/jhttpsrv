package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETparamsRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class EchoGETParamsHandlerTest {
    private EchoGETParamsHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new EchoGETParamsHandler();
    }

    //@Test
    public void testRun() throws Exception {
        Request request = TestRequestMaker.fromString(GETparamsRequest.ENTIRE_MESSAGE);
        Response response = handler.run(request);
        String content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        assertThat(content, containsString("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
        assertThat(content, containsString("variable_2 = stuff"));
    }
}