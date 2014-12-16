package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.handler.OKRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class RouterTest {
    private Router router;
    private Request request;

    @Before
    public void setUp() throws Exception {
        RequestParser parser = new RequestParser();
        InputStream is = new ByteArrayInputStream(GETRequestChrome.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8));

        router = new Router();
        request = parser.parseInputStream(is);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDefaultRoute() throws Exception {
        // A new Router with no routes established should return the default default handler, the OKRequestHandler.
        RequestHandler handler = router.route(request);
        Assert.assertThat(handler, instanceOf(OKRequestHandler.class));
    }

    @Test
    public void route() throws Exception {
        // TODO: This is tested by testDefaultRoute() and addRoute(), but add more detailed tests here later.
    }

    @Test
    public void addRoute() throws Exception {
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", HelloWorldRequestHandler.class);
        RequestHandler handler = router.route(request);
        Assert.assertThat(handler, instanceOf(HelloWorldRequestHandler.class));
    }
}