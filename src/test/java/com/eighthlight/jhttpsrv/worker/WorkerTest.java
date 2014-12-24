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

        /* TODO:  This should be a bad request, not a 404.
        正  ~   nc localhost 8080
lknelkn lknnnnn lknelnel



HTTP/1.1 404 Not Found

正  ~

         */

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
    public void testHandleBadRequest() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream("GET /form.html ");

        worker = new Worker(socket, router);
        worker.run();

        Assert.assertEquals(StatusCodes.BAD_REQUEST, worker.getResponse().getStatusCode());
        Assert.assertTrue(socket.isClosed());
    }

}