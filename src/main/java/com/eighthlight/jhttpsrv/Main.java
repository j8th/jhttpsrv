package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.handler.*;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.server.Jhttpsrv;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    private static ServerSocket serverSocket;
    private static Router router;
    private static Jhttpsrv jhttpsrv;

    public static void init(ServerSocket myServerSocket) throws IOException {
        serverSocket = myServerSocket;
        router = new Router();

        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/redirect", new RedirectRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", new TimeRequestHandler());
        router.setDefaultRequestHandler(new FileRequestHandler());

        jhttpsrv = new Jhttpsrv(serverSocket, router);
    }

    public static Jhttpsrv getJhttpsrv() {
        return jhttpsrv;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket myServerSocket = new ServerSocket(8080);
        init(myServerSocket);

        jhttpsrv.run();
    }
}
