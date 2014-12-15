package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by jason on 12/15/14.
 */
public class Jhttpsrv {
    //private ServerSocket serverSocket;
    //private Router router;

    private RequestParser requestParser = new RequestParser();
    private ResponseBuilder responseBuilder = new ResponseBuilder();


    /* Constructors */
    //public Jhttpsrv(ServerSocket myServerSocket, Router myRouter) {
        //serverSocket = myServerSocket;
        //router = myRouter;

        //requestParser = new RequestParser();
        //responseBuilder = new ResponseBuilder();
    //}




    /* Public Methods */
    public void handle(Socket mySocket) throws IOException {
        InputStream is = mySocket.getInputStream();
        OutputStream os = mySocket.getOutputStream();

        Request request = requestParser.parseInputStream(is);
        RequestHandler handler = new HelloWorldRequestHandler();
        Response response = handler.run(request);

        String responseString = responseBuilder.buildResponse(response);
        os.write(responseString.getBytes(StandardCharsets.UTF_8.toString()));
    }
//
//    public void start() {
//        while(true){
//            Socket mySocket = serverSocket.accept();
//            handle(mySocket);
//        }
//    }
}
