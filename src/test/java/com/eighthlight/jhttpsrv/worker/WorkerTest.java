package com.eighthlight.jhttpsrv.worker;

import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.mocks.MockSocket;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.shared.ProtocolIntegers;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class WorkerTest {
    private Worker worker;
    private Router router;

    @Before
    public void setUp() throws Exception {
        router = new Router();
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", new HelloWorldRequestHandler());
    }

    @Test
    public void testRun() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream(GETHelloworldRequest.ENTIRE_MESSAGE);

        worker = new Worker(socket, router);
        worker.run();

        Assert.assertEquals(GETHelloworldResponse.ENTIRE_MESSAGE, socket.getOutputStreamAsString());
        Assert.assertTrue(socket.isClosed());
    }

    @Test
    public void testHandleEmptySocket() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream("");

        worker = new Worker(socket, router);
        worker.run();

        // TODO: Assert that the socket's outputstream actually gets the response...
        //Assert.assertEquals("", socket.getOutputStreamAsString());
        Assert.assertEquals(StatusCodes.BAD_REQUEST, worker.getResponse().getStatusCode());
        Assert.assertTrue(socket.isClosed());
    }

    @Test
    public void testGetResponse() {
        MockSocket socket = new MockSocket();
        socket.seedInputStream(GETHelloworldRequest.ENTIRE_MESSAGE);

        worker = new Worker(socket, router);

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

            worker = new Worker(socket, router);
            worker.run();

            Assert.assertEquals("Failing Bad Request Text: '" + badRequest + "'", StatusCodes.BAD_REQUEST, worker.getResponse().getStatusCode());
            Assert.assertTrue(socket.isClosed());
        }
    }

}