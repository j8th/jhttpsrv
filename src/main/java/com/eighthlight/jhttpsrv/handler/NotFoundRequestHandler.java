package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;

public class NotFoundRequestHandler implements RequestHandler {
    public Response run(Request request) {
        Response response = new Response();

        response.setStatusCode(StatusCodes.NOT_FOUND);

        return response;
    }
}
