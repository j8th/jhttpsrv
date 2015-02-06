package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.worker.Worker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Router router;
    private RequestParser parser;
    private Logger logger;

    public Server(ServerSocket serverSocket, Router router, Logger logger, RequestParser parser) {
        this.serverSocket = serverSocket;
        this.router = router;
        this.logger = logger;
        this.parser = parser;
    }

    public void run() {
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Worker worker = new Worker(socket, parser, router, logger);
                new Thread(worker).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}