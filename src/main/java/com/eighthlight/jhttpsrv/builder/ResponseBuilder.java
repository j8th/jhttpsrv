package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;

/**
 * Created by jason on 12/10/14.
 */
public class ResponseBuilder {
    public String buildStatusLine(Response response) {
        String result = String.format("HTTP/%s %s %s\r\n",
                response.getProtocolVersion(),
                response.getStatusCode(),
                response.getReasonPhrase());

        return result;
    }

    public byte[] buildResponse(Response response) {
        return new byte[0];
    }
}
