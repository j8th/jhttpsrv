package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.config.Config;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.worker.Worker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Router router;
    private RequestParser parser;
    private Logger logger;

    public Server(ServerSocket serverSocket, Router router, Logger logger, Config config) {
        this.serverSocket = serverSocket;
        this.router = router;
        this.logger = logger;
        try {
            URL context = new URL(String.format("http://localhost:%d/", config.getPort()));
            parser = new RequestParser(context);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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