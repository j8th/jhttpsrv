package com.eighthlight.jhttpsrv.response;

/**
 * Created by jason on 12/11/14.
 */
public class ResponseHeader {
    private String contenttype = "";
    private int contentlength = 0;

    public String getContentType() {
        return contenttype;
    }

    public void setContentType(String myContentType) {
        contenttype = myContentType;
    }

    public int getContentLength() {
        return contentlength;
    }

    public void setContentLength(int length) {
        contentlength = length;
    }
}
