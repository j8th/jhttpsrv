package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.client.SimpleHttpClient;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.logger.MemoryLogger;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.router.Router;
import static org.junit.Assert.*;

import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;
import java.net.URL;

public class ServerTest {
    private ServerSocket serverSocket;
    private Router router;
    private Logger logger;
    private Server server;
    private RequestParser parser;

    @Before
    public void setUp() throws Exception {
        serverSocket = new ServerSocket(8081);
        logger = new MemoryLogger();
        router = new Router();
        parser = new RequestParser(new URL("http://localhost:8081/"));

        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", new HelloWorldRequestHandler());

        server = new Server(serverSocket, router, logger, parser);
    }

    @Test
    public void testRun() throws Exception {
        new Thread(server).start();

        int waitTimeMilliseconds = 3 * 1000;
        int numClients = 2;
        for(int i = 0; i < numClients; i++) {
            SimpleHttpClient simpleHttpClient = new SimpleHttpClient("http://localhost:8081/helloworld");
            simpleHttpClient.setReadTimeout(waitTimeMilliseconds);
            simpleHttpClient.run();
            assertEquals(String.format("Client #%d failed: ", i), StatusCodes.OK, simpleHttpClient.getResponseCode());
            assertEquals(String.format("Client #%d failed: ", i), GETHelloworldResponse.BODY, simpleHttpClient.getContent());
        }
    }
}