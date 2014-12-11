package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.shared.StatusCodes;

/**
 * Created by jason on 12/10/14.
 */
public class Response {
    private static final String HTTP_VERSION = "1.1";



    private int statuscode = 0;



    public String getProtocolVersion() {
        return HTTP_VERSION;
    }

    public void setStatusCode(int code){
        statuscode = code;
    }

    public int getStatusCode() {
        return statuscode;
    }

    public String getReasonPhrase() {
        return StatusCodes.CodeToPhrase(statuscode);
    }
}
