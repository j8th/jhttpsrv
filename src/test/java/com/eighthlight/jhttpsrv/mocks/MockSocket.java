package com.eighthlight.jhttpsrv.mocks;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by jason on 12/15/14.
 */
public class MockSocket extends Socket {
    private ByteArrayOutputStream os = new ByteArrayOutputStream();
    private ByteArrayInputStream is = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));



    /* Public Methods */
    public OutputStream getOutputStream() {
        return os;
    }

    public InputStream getInputStream() {
        return is;
    }

    public void seedInputStream(String myString) {
        is = new ByteArrayInputStream(myString.getBytes(StandardCharsets.UTF_8));
    }

    public String getOutputStreamAsString() {
        try {
            return os.toString(StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
