package com.eighthlight.jhttpsrv.multithreading;

import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadingHttpClient implements Runnable {
    private String url;
    private int responseCode = 0;

    public ThreadingHttpClient(String url) {
        this.url = url;
    }

    public void run() {
        try {
            URL urlobj = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlobj.openConnection();
            httpURLConnection.connect();
            responseCode = httpURLConnection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getResponseCode() {
        return responseCode;
    }
}