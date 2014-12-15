package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.router.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.router.RequestHandler;
import com.eighthlight.jhttpsrv.router.Router;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jason on 12/15/14.
 */
public class Jhttpsrv {
    //private ServerSocket serverSocket;
    //private Router router;

    //private RequestParser requestParser;
    //private ResponseBuilder responseBuilder;


    /* Constructors */
    //public Jhttpsrv(ServerSocket myServerSocket, Router myRouter) {
        //serverSocket = myServerSocket;
        //router = myRouter;

        //requestParser = new RequestParser();
        //responseBuilder = new ResponseBuilder();
    //}




    /* Public Methods */
//    public void handle(Socket mySocket) {
//        InputStream is = mySocket.getInputStream();
//        OutputStream os = mySocket.getOutputStream();
//
//        Request request = requestParser.parseInputStream(is);
//        RequestHandler handler = router.route(request);
//        Response response = handler.run(request);
//
//        String responseString = responseBuilder.buildResponse(response);
//        os.write(responseString.getBytes("UTF-8"));
//    }
//
//    public void start() {
//        while(true){
//            Socket mySocket = serverSocket.accept();
//            handle(mySocket);
//        }
//    }
}
