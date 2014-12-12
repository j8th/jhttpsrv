package com.eighthlight.jhttpsrv.response;

import java.io.UnsupportedEncodingException;

/**
 * Created by jason on 12/11/14.
 */
public class ResponseBody {
    private String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String myContent) {
        content = myContent;
    }

    public int getContentLength() {
        if(content == null)
            return 0;
        try {
            // TODO: Figure out how we want to handle our character sets...
            byte[] bytes = content.getBytes("UTF-8");
            return bytes.length;
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
