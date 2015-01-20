package com.eighthlight.jhttpsrv.multithreading;

import com.eighthlight.jhttpsrv.client.SimpleHttpClient;
import com.eighthlight.jhttpsrv.config.Config;
import com.eighthlight.jhttpsrv.config.Setup;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.handler.TimeRequestHandler;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.logger.MemoryLogger;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.server.Jhttpsrv;
import static org.junit.Assert.*;
import org.junit.Test;

import java.net.ServerSocket;

public class MultiThreadingTest {
    @Test
    public void testMultithreadingTime() throws Exception {
        Logger logger = new MemoryLogger();
        Config config = new Setup().getConfig();
        ServerSocket serverSocket = new ServerSocket(config.getPort());
        Router router = new Router();
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", new TimeRequestHandler());
        Jhttpsrv jhttpsrv = new Jhttpsrv(serverSocket, router, logger, config);

        SimpleHttpClient[] clients = new SimpleHttpClient[20];
        for(int i = 0; i < clients.length; i++)
            clients[i] = new SimpleHttpClient("http://localhost:8080/time");

        new Thread(jhttpsrv).start();
        for(int i = 0; i < clients.length; i++)
            new Thread(clients[i]).start();

        Thread.sleep(2000);

        for(int i = 0; i < clients.length; i++) {
            try {
                assertEquals(String.format("Client #%d did not get an OK response yet.", i), StatusCodes.OK, clients[i].getResponseCode());
            } catch (AssertionError e) {
                for(int x = 0; x < clients.length; x++)
                    System.out.println(String.format("Client #%d Return Code: %d", x, clients[x].getResponseCode()));
                throw e;
            }
        }
    }
}
