package com.eighthlight.jhttpsrv.request;

import java.util.Map;

/**
 * Created by jason on 12/8/14.
 */
public class Request {
    private Header header;

    public Request(Map<String, String> myRequestLine, Header myHeader, Body myBody) {
        header = myHeader;
    }

    public Header getHeader() {
        return header;
    }
}
