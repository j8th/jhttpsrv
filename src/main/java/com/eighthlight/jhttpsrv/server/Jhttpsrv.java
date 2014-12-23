package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.router.Router;

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

    public void handle(Socket mySocket) throws IOException {
        InputStream is = mySocket.getInputStream();
        OutputStream os = mySocket.getOutputStream();

        Request request = requestParser.parseInputStream(is);
        if(request.isEmpty()) {
            mySocket.close();
            return;
        }
        RequestHandler handler = router.handlerByRoute(request);
        Response response = handler.run(request);

        byte[] responseBytes = responseBuilder.buildResponse(response);
        os.write(responseBytes);
        mySocket.close();
    }

    public void run() {
        while(true){
            try {
                Socket mySocket = serverSocket.accept();
                handle(mySocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}