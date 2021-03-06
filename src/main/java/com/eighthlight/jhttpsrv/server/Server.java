package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.worker.Worker;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private PrintStream printStream;
    private ServerSocket serverSocket;
    private Router router;
    private RequestParser parser;
    private ResponseBuilder builder;
    private Logger logger;

    public Server(PrintStream printStream, ServerSocket serverSocket, Router router,
                  Logger logger, RequestParser parser, ResponseBuilder builder) {
        this.printStream = printStream;
        this.serverSocket = serverSocket;
        this.router = router;
        this.logger = logger;
        this.parser = parser;
        this.builder = builder;
    }

    public void run() {
        printStream.println(String.format("Server started on port %d...", serverSocket.getLocalPort()));
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Worker worker = new Worker(socket, router, logger, parser, builder);
                new Thread(worker).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}