package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.handler.FileRequestHandler;
import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.handler.OKRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.instanceOf;

public class RouterTest {
    private Router router;
    private Request request;

    @Before
    public void setUp() throws Exception {
        RequestParser parser = new RequestParser();
        InputStream is = new ByteArrayInputStream(GETHelloworldRequest.ENTIRE_MESSAGE.getBytes(StandardCharsets.UTF_8));

        router = new Router();
        request = parser.parseInputStream(is);
    }

    @Test
    public void testDefaultRoute() throws Exception {
        RequestHandler handler = router.route(request);
        Assert.assertThat(handler, instanceOf(OKRequestHandler.class));
    }

    @Test
    public void testSetDefaultRouteHandler() throws Exception {
        router.setDefaultRouteHandler(FileRequestHandler.class);
        RequestHandler handler = router.route(request);
        Assert.assertThat(handler, instanceOf(FileRequestHandler.class));
    }

    @Test
    public void testAddRoute() throws Exception {
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", HelloWorldRequestHandler.class);
        RequestHandler handler = router.route(request);
        Assert.assertThat(handler, instanceOf(HelloWorldRequestHandler.class));
    }
}