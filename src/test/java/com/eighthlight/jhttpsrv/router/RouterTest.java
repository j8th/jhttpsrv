package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.handler.*;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETindexhtmlRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.TestRequestMaker;
import static org.junit.Assert.*;

import com.eighthlight.jhttpsrv.testmessage.chrome.datahandler.POSTDataRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class RouterTest {
    private Router router;
    private Request request;
    private String rootDirPath;

    @Before
    public void setUp() throws Exception {
        router = new Router();
        request = TestRequestMaker.fromString(GETHelloworldRequest.ENTIRE_MESSAGE);
        rootDirPath = System.getProperty("user.dir") + "/www";
    }

    @Test
    public void testDefaultRoute() throws Exception {
        RequestHandler handler = router.handlerByRoute(request);
        assertThat(handler, instanceOf(NotFoundRequestHandler.class));
    }

    @Test
    public void testAddRoute() throws Exception {
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", new HelloWorldRequestHandler());
        RequestHandler handler = router.handlerByRoute(request);
        assertThat(handler, instanceOf(HelloWorldRequestHandler.class));
    }

    @Test
    public void testAddRegexRoute() throws Exception {
        HelloWorldRequestHandler helloWorldRequestHandler = new HelloWorldRequestHandler();
        router.addRegexRoute(ProtocolStrings.HTTP_METHOD_GET, ".*", helloWorldRequestHandler);

        RequestHandler handler = router.handlerByRoute(request);

        assertEquals(helloWorldRequestHandler, handler);
    }

    @Test
    public void testStringRouteMatchesBeforeCatchallRegexRoute() throws Exception {
        Request helloWorldRequest = TestRequestMaker.fromString(GETHelloworldRequest.ENTIRE_MESSAGE);
        Request indexRequest = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);
        HelloWorldRequestHandler helloWorldRequestHandler = new HelloWorldRequestHandler();
        FileRequestHandler fileRequestHandler = new FileRequestHandler(rootDirPath);
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", helloWorldRequestHandler);
        router.addRegexRoute(ProtocolStrings.HTTP_METHOD_GET, "/.*", fileRequestHandler);

        assertEquals(helloWorldRequestHandler, router.handlerByRoute(helloWorldRequest));
        assertEquals(fileRequestHandler, router.handlerByRoute(indexRequest));
    }

    @Test
    public void testMatchingNeitherRouteNorVerbReturns404Handler() throws Exception {
        request = TestRequestMaker.fromString(POSTDataRequest.ENTIRE_MESSAGE);
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/index.html", new FileRequestHandler(rootDirPath));

        RequestHandler requestHandler = router.handlerByRoute(request);

        assertThat(requestHandler, instanceOf(NotFoundRequestHandler.class));
    }

    @Test
    public void testNoMatchingRoute_MatchingVerb_Returns404Handler() throws Exception {
        request = TestRequestMaker.fromString(POSTDataRequest.ENTIRE_MESSAGE);
        router.addRoute(ProtocolStrings.HTTP_METHOD_POST, "/not-so-restful", new DataHandler());

        RequestHandler requestHandler = router.handlerByRoute(request);

        assertThat(requestHandler, instanceOf(NotFoundRequestHandler.class));
    }

    @Test
    public void testMatchingRoute_NoMatchingVerbReturns405Handler() throws Exception {
        request = TestRequestMaker.fromString(POSTDataRequest.ENTIRE_MESSAGE);
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/restful", new DataHandler());

        RequestHandler requestHandler = router.handlerByRoute(request);

        assertThat(requestHandler, instanceOf(MethodNotAllowedRequestHandler.class));
    }
}