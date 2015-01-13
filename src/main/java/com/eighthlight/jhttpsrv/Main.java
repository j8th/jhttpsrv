package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.args.CmdArgs;
import com.eighthlight.jhttpsrv.handler.*;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.server.Jhttpsrv;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        CmdArgs cmdArgs = new CmdArgs(args);

        ServerSocket serverSocket;
        int port;
        try {
            port = Integer.parseInt(cmdArgs.getPort());
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("Port must be an integer between 0 and 65,535.");
            return;
        }

        try {
            FileRequestHandler.setRootDir(cmdArgs.getDirectory());
        } catch (IllegalArgumentException e) {
            System.err.println("The www root directory must be a readable directory.");
            return;
        }

        Router router = new Router();
        String redirectURL = String.format("http://localhost:%d/", port);
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/redirect", new RedirectRequestHandler(redirectURL));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", new TimeRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_POST, "/form", new FormRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_PUT, "/form", new FormRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_OPTIONS, "/method_options", new OptionsRequestHandler());
        router.setDefaultRequestHandler(new FileRequestHandler());

        Jhttpsrv jhttpsrv = new Jhttpsrv(serverSocket, router);
        jhttpsrv.run();
    }
}
