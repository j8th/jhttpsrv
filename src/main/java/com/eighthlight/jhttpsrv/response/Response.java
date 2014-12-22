package com.eighthlight.jhttpsrv.response;

import com.eighthlight.jhttpsrv.shared.StatusCodes;

public class Response {
    private static final String HTTP_VERSION = "1.1";

    private int statuscode = 0;
    private ResponseHeader header = new ResponseHeader();
    private ResponseBody body = new ResponseBody();

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

    public void setHeaders(ResponseHeader myHeader) {
        header = myHeader;
    }

    public ResponseHeader getHeaders() {
        return header;
    }

    public void setBody(ResponseBody myBody) {
        body = myBody;
    }

    public ResponseBody getBody() {
        return body;
    }
}