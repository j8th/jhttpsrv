package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.worker.Worker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Jhttpsrv implements Runnable {
    private ServerSocket serverSocket;
    private Router router;

    private RequestParser requestParser = new RequestParser();
    private ResponseBuilder responseBuilder = new ResponseBuilder();

    public Jhttpsrv(ServerSocket myServerSocket, Router myRouter) {
        serverSocket = myServerSocket;
        router = myRouter;
    }

    public void run() {
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Worker worker = new Worker(socket, router);
                worker.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}