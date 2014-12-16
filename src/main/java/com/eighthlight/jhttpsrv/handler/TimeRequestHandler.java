package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jason on 12/11/14.
 *
 * The TimeRequestHandler returns the current time in the message body.
 */
public class TimeRequestHandler implements RequestHandler {
    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        ZoneId zoneId = ZoneId.of("US/Central");
        ZonedDateTime zdt = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz");
        String now = zdt.format(formatter);

        body.setContent(now);
        header.setContentType(MIMETypes.HTML);
        header.setContentLength(body.getContentLength());

        response.setStatusCode(StatusCodes.OK);
        response.setHeaders(header);
        response.setBody(body);

        return response;
    }
}
