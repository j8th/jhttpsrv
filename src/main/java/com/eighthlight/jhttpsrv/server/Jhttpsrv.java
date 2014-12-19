package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by jason on 12/15/14.
 */
public class Jhttpsrv implements Runnable {
    private ServerSocket serverSocket;
    private Router router;

    private RequestParser requestParser = new RequestParser();
    private ResponseBuilder responseBuilder = new ResponseBuilder();


    /* Constructors */
    public Jhttpsrv(ServerSocket myServerSocket, Router myRouter) {
        serverSocket = myServerSocket;
        router = myRouter;
    }




    /* Public Methods */
    public void handle(Socket mySocket) throws IOException {
        InputStream is = mySocket.getInputStream();
        OutputStream os = mySocket.getOutputStream();

        Request request = requestParser.parseInputStream(is);
        // If the client didn't send us any data (the socket is "empty" and therefore the request is "empty"),
        //    then just close this connection without saying anything back and quit.
        if(request.isEmpty()) {
            mySocket.close();
            return;
        }
        RequestHandler handler = router.route(request);
        Response response = handler.run(request);

        String responseString = responseBuilder.buildResponse(response);
        os.write(responseString.getBytes(StandardCharsets.UTF_8.toString()));
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
