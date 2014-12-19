package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.mocks.MockSocket;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.ServerSocket;

import static org.mockito.Mockito.mock;

public class JhttpsrvTest {
    private ServerSocket mockServerSocket;
    private Router router;
    private Jhttpsrv jhttpsrv;

    @Before
    public void setUp() throws Exception {
        mockServerSocket = mock(ServerSocket.class);
        router = new Router();
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", HelloWorldRequestHandler.class);
        jhttpsrv = new Jhttpsrv(mockServerSocket, router);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void handle() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream(GETHelloworldRequest.ENTIRE_MESSAGE);
        jhttpsrv.handle(socket);

        Assert.assertEquals(GETHelloworldResponse.ENTIRE_MESSAGE, socket.getOutputStreamAsString());
        Assert.assertTrue(socket.isClosed());
    }

    /*
     * Google Chrome will sometimes make a connection with an "empty" socket;
     *     the InputStream we get from it blocks on the first read(), and then
     *     eventually returns a -1 indicating the end of the stream.
     *
     * This was causing the server to crash with an ArrayIndexOutOfBoundsException from
     *     RequestParser.parseRequestLine().
     *
     * This test exists to replicate that scenario and ensure it's fixed.
     */
    @Test
    public void testHandleEmptySocket() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream("");
        jhttpsrv.handle(socket);

        Assert.assertEquals("", socket.getOutputStreamAsString());
        Assert.assertTrue(socket.isClosed());
    }

    @Test
    public void run() throws Exception {
        new Thread(jhttpsrv).start();
        Mockito.verify(mockServerSocket).accept();
    }
}