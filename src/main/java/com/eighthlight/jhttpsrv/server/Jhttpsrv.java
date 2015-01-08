package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.worker.Worker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Jhttpsrv implements Runnable {
    private ServerSocket serverSocket;
    private Router router;

    public Jhttpsrv(ServerSocket myServerSocket, Router myRouter) {
        serverSocket = myServerSocket;
        router = myRouter;
    }

    public void run() {
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Worker worker = new Worker(socket, router);
                new Thread(worker).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}