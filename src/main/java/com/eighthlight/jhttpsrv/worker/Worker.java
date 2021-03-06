package com.eighthlight.jhttpsrv.worker;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.logger.Logger;
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

public class Worker implements Runnable {
    private Socket socket;
    private Router router;
    private Logger logger;
    private RequestParser parser;
    private ResponseBuilder builder;
    private Response response;

    public Worker(Socket socket, Router router, Logger logger, RequestParser parser, ResponseBuilder builder) {
        this.socket = socket;
        this.router = router;
        this.logger = logger;
        this.parser = parser;
        this.builder = builder;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            Request request = parser.parseInputStream(is);
            logger.log(request.getRequestLine());
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
