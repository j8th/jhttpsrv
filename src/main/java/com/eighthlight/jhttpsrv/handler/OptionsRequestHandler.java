package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;

public class OptionsRequestHandler implements RequestHandler {
    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        response.setHeaders(header);
        response.setBody(body);

        response.setStatusCode(StatusCodes.OK);
        header.setAllow(new String[]{
                ProtocolStrings.HTTP_METHOD_GET,
                ProtocolStrings.HTTP_METHOD_HEAD,
                ProtocolStrings.HTTP_METHOD_POST,
                ProtocolStrings.HTTP_METHOD_OPTIONS,
                ProtocolStrings.HTTP_METHOD_PUT
        });

        return response;
    }
}
