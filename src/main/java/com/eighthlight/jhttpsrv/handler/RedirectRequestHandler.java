package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

/**
 * Created by jason on 12/16/14.
 *
 * The RedirectRequestHandler simply redirects the client to another url.
 */
public class RedirectRequestHandler implements RequestHandler {

    @Override
    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();

        response.setStatusCode(StatusCodes.TEMPORARY_REDIRECT);
        header.setLocation("/time");
        response.setHeaders(header);

        return response;
    }
}
