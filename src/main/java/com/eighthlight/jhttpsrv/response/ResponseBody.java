package com.eighthlight.jhttpsrv.response;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ResponseBody {
    private byte[] content = new byte[0];

    public byte[] getContent() {
        return content;
    }

    public void setContent(String myContent) throws IllegalArgumentException {
        if(myContent == null)
            throw new IllegalArgumentException("myContent cannot be null.");
        content = myContent.getBytes(StandardCharsets.UTF_8);
    }

    public void setContent(byte[] myContent) throws IllegalArgumentException {
        if(myContent == null)
            throw new IllegalArgumentException("myContent cannot be null.");
        content = myContent;
    }

    public int getContentLength() {
        return content.length;
    }
}