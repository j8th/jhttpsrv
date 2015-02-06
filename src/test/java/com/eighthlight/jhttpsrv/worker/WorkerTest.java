package com.eighthlight.jhttpsrv.worker;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.logger.MemoryLogger;
import com.eighthlight.jhttpsrv.mocks.MockSocket;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import com.eighthlight.jhttpsrv.testmessage.chrome.POSTFormRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.PUTtextfileRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;

public class WorkerTest {
    private Worker worker;
    private RequestParser parser;
    private ResponseBuilder builder;
    private Router router;
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        URL context = new URL("http://localhost:8080/");
        parser = new RequestParser(context);
        builder = new ResponseBuilder();
        router = new Router();
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", new HelloWorldRequestHandler());
        logger = new MemoryLogger();
    }

    @Test
    public void testRun() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream(GETHelloworldRequest.ENTIRE_MESSAGE);

        worker = new Worker(socket, parser, builder, router, logger);
        worker.run();

        Assert.assertEquals(GETHelloworldResponse.ENTIRE_MESSAGE, socket.getOutputStreamAsString());
        Assert.assertTrue(socket.isClosed());
    }

    @Test
    public void testHandleEmptySocket() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream("");

        worker = new Worker(socket, parser, builder, router, logger);
        worker.run();

        Assert.assertNotEquals("", socket.getOutputStreamAsString());
        Assert.assertEquals(StatusCodes.BAD_REQUEST, worker.getResponse().getStatusCode());
        Assert.assertTrue(socket.isClosed());
    }

    @Test
    public void testGetResponse() {
        MockSocket socket = new MockSocket();
        socket.seedInputStream(GETHelloworldRequest.ENTIRE_MESSAGE);

        worker = new Worker(socket, parser, builder, router, logger);
        Assert.assertNull(worker.getResponse());
        worker.run();
        Assert.assertThat(worker.getResponse(), instanceOf(Response.class));
    }

    @Test
    public void testHandleBadRequests() throws Exception {
        List<String> badRequests = new ArrayList<String>();
        badRequests.add("GET /form.html ");
        badRequests.add("lknelkn lknnnnn lknelnel\r\n");

        for(String badRequest : badRequests) {
            MockSocket socket = new MockSocket();
            socket.seedInputStream(badRequest);

            worker = new Worker(socket, parser, builder, router, logger);
            worker.run();

            Assert.assertEquals("Failing Bad Request Text: '" + badRequest + "'", StatusCodes.BAD_REQUEST, worker.getResponse().getStatusCode());
            Assert.assertTrue(socket.isClosed());
        }
    }

    @Test
    public void testLogsRequestLines() {
        MockSocket socket1 = new MockSocket();
        MockSocket socket2 = new MockSocket();
        MockSocket socket3 = new MockSocket();
        socket1.seedInputStream(GETHelloworldRequest.ENTIRE_MESSAGE);
        socket2.seedInputStream(PUTtextfileRequest.ENTIRE_MESSAGE);
        socket3.seedInputStream(POSTFormRequest.ENTIRE_MESSAGE);

        worker = new Worker(socket1, parser, builder, router, logger);
        worker.run();
        worker = new Worker(socket2, parser, builder, router, logger);
        worker.run();
        worker = new Worker(socket3, parser, builder, router, logger);
        worker.run();

        List<String> messages = logger.getMessages();
        List<String> expectedLogMessages = new ArrayList<String>(3);
        expectedLogMessages.add("GET /helloworld HTTP/1.1");
        expectedLogMessages.add("PUT /plain.txt HTTP/1.1");
        expectedLogMessages.add("POST /formsubmit HTTP/1.1");

        for(String message : expectedLogMessages)
            Assert.assertTrue(String.format("Logs did not contain expected message: \"%s\"", message), messages.contains(message));
    }

}