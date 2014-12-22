package com.eighthlight.jhttpsrv.response;

public class ResponseHeader {
    private String contenttype = "";
    private int contentlength = 0;
    private String location = "";

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String myLocation) {
        location = myLocation;
    }
}