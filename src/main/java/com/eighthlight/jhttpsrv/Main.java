package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.server.Jhttpsrv;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by jason on 12/4/14.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket myServerSocket = new ServerSocket(80);
        Jhttpsrv server = new Jhttpsrv(myServerSocket);
        server.run();
    }

}
