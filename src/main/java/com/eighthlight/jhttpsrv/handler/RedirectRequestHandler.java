package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.constants.StatusCodes;

public class RedirectRequestHandler implements RequestHandler {
    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();

        response.setStatusCode(StatusCodes.TEMPORARY_REDIRECT);
        header.setLocation("/time");
        response.setHeaders(header);

        return response;
    }
}