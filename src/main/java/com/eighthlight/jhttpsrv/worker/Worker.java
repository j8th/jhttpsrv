package com.eighthlight.jhttpsrv.worker;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.constants.StatusCodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

public class Worker implements Runnable {
    private Socket socket;
    private RequestParser parser;
    private Router router;
    private Response response;

    public Worker(Socket socket, RequestParser parser, Router router) {
        this.socket = socket;
        this.parser = parser;
        this.router = router;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            ResponseBuilder builder = new ResponseBuilder();

            Request request = parser.parseInputStream(is);
            if (request.isValid()) {
                RequestHandler handler = router.handlerByRoute(request);
                response = handler.run(request);
            } else {
                response = new Response();
                ResponseHeader header = new ResponseHeader();
                ResponseBody body = new ResponseBody();

                response.setStatusCode(StatusCodes.BAD_REQUEST);

                response.setHeaders(header);
                response.setBody(body);
            }

            byte[] responseBytes = builder.buildResponse(response);
            os.write(responseBytes);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response getResponse() {
        return response;
    }
}
