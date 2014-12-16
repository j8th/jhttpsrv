package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

/**
 * Created by jason on 12/16/14.
 *
 * The OKRequestHandler always returns a Response with a status code of 200 OK,
 * a few basic headers, and an empty body.
 */
public class OKRequestHandler implements RequestHandler {

    @Override
    public Response run(Request request) {
        Response result = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        result.setStatusCode(StatusCodes.OK);
        body.setContent("");
        header.setContentType(MIMETypes.HTML);
        header.setContentLength(body.getContentLength());

        result.setHeaders(header);
        result.setBody(body);
        return result;
    }
}
