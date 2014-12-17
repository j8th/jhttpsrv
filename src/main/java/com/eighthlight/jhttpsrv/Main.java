package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.handler.FormRequestHandler;
import com.eighthlight.jhttpsrv.handler.HelloWorldRequestHandler;
import com.eighthlight.jhttpsrv.handler.RedirectRequestHandler;
import com.eighthlight.jhttpsrv.handler.TimeRequestHandler;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.server.Jhttpsrv;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by jason on 12/4/14.
 */
public class Main {
    private static ServerSocket serverSocket;
    private static Router router;
    private static Jhttpsrv jhttpsrv;

    public static void init(ServerSocket myServerSocket) throws IOException {
        serverSocket = myServerSocket;
        router = new Router();

        // Setup the routes.
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/redirect", RedirectRequestHandler.class);
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", TimeRequestHandler.class);
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/form", FormRequestHandler.class);

        jhttpsrv = new Jhttpsrv(serverSocket, router);
    }

    public static Jhttpsrv getJhttpsrv() {
        return jhttpsrv;
    }



    public static void main(String[] args) throws IOException {
        ServerSocket myServerSocket = new ServerSocket(80);
        init(myServerSocket);

        jhttpsrv.run();
    }

}
