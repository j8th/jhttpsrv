package com.eighthlight.jhttpsrv.client;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleHttpClient implements Runnable {
    private String url;
    private int readTimeout = 0;

    private int responseCode = 0;
    private String content = "";

    public SimpleHttpClient(String url) {
        this.url = url;
    }

    public void run() {
        try {
            URL urlobj = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlobj.openConnection();
            httpURLConnection.setReadTimeout(readTimeout);

            httpURLConnection.connect();

            responseCode = httpURLConnection.getResponseCode();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            int contentLength = httpURLConnection.getContentLength();
            if(contentLength > 0) {
                byte[] bodyBytes = new byte[contentLength];
                bufferedInputStream.read(bodyBytes, 0, contentLength);
                content = new String(bodyBytes, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getContent() {
        return content;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}