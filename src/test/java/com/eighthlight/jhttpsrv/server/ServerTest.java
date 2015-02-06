package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.client.SimpleHttpClient;
import com.eighthlight.jhttpsrv.config.Config;
import com.eighthlight.jhttpsrv.config.Setup;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.logger.MemoryLogger;
import com.eighthlight.jhttpsrv.router.Router;
import static org.junit.Assert.*;

import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldResponse;
import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;

public class ServerTest {
    private ServerSocket serverSocket;
    private Router router;
    private Logger logger;
    private Config config;
    private Server server;

    @Before
    public void setUp() throws Exception {
        serverSocket = new ServerSocket(8081);
        logger = new MemoryLogger();
        config = new Setup().getConfig();
        router = new Router();

        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/helloworld", new HelloWorldRequestHandler());

        server = new Server(serverSocket, router, logger, config);
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