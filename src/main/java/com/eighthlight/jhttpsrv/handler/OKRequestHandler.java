package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

public class OKRequestHandler implements RequestHandler {
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