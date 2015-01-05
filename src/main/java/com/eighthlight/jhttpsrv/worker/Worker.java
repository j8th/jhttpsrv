package com.eighthlight.jhttpsrv.worker;

import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket socket;
    private Router router;
    private Response response;

    public Worker(Socket mySocket, Router myRouter) {
        socket = mySocket;
        router = myRouter;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            RequestParser parser = new RequestParser();
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
