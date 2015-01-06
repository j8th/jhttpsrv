package com.eighthlight.jhttpsrv.request;

public class RequestBody {
    private String rawBody;

    public RequestBody(String myRawBody) {
        rawBody = myRawBody;
    }

    public boolean isEmpty() {
        return rawBody.equals("");
    }

    public String getContent() {
        return rawBody;
    }
}