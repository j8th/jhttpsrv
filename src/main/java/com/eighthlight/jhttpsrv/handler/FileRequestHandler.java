package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;

/**
 * Created by jason on 12/11/14.
 */
public class FileRequestHandler implements RequestHandler {
    public Response run(Request request) {
        return new Response();
    }
}
