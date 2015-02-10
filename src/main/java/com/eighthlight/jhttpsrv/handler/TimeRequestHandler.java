package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.constants.MIMETypes;
import com.eighthlight.jhttpsrv.constants.StatusCodes;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeRequestHandler implements RequestHandler {
    private int waitTimeMilliseconds;

    public TimeRequestHandler(int waitTimeMilliseconds) {
        this.waitTimeMilliseconds = waitTimeMilliseconds;
    }

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        try {
            Thread.sleep(waitTimeMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ZoneId zoneId = ZoneId.of("US/Central");
        ZonedDateTime zdt = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm zzz");
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