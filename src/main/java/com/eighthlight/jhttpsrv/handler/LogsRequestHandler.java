package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;

import java.util.List;

public class LogsRequestHandler implements RequestHandler {
    private Logger logger;

    public LogsRequestHandler(Logger logger) {
        this.logger = logger;
    }

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();
        response.setHeaders(header);
        response.setBody(body);

        response.setStatusCode(StatusCodes.OK);

        List<String> messages = logger.getMessages();
        String content = "";
        for(String m : messages)
            content += String.format("%s\n", m);

        body.setContent(content);

        return response;
    }
}
