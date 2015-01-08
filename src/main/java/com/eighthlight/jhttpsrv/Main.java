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
        try {
            int port = Integer.parseInt(cmdArgs.getPort());
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
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/redirect", new RedirectRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", new TimeRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_POST, "/formsubmit", new FormRequestHandler());
        router.setDefaultRequestHandler(new FileRequestHandler());

        Jhttpsrv jhttpsrv = new Jhttpsrv(serverSocket, router);
        jhttpsrv.run();
    }
}
