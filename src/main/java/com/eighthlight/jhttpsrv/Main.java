package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.args.CmdArgs;
import com.eighthlight.jhttpsrv.config.Config;
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
        Config config;
        try {
            int port = Integer.parseInt(cmdArgs.getPort());
            config = new Config(port);
            serverSocket = new ServerSocket(config.getPort());
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
        String redirectURL = String.format("http://localhost:%d/", config.getPort());
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/redirect", new RedirectRequestHandler(redirectURL));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", new TimeRequestHandler());
        DataHandler dataHandler = new DataHandler();
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_POST, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_PUT, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_DELETE, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_OPTIONS, "/method_options", new OptionsRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/parameters", new EchoGETParamsHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_PATCH, "/patch-content.txt", new PatchHandler(new FileRequestHandler()));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/partial_content.txt", new RangeHandler(new FileRequestHandler()));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/logs", new BasicAuthHandler(new HelloWorldRequestHandler(), "admin", "hunter2"));
        router.setDefaultRequestHandler(new FileRequestHandler());

        Jhttpsrv jhttpsrv = new Jhttpsrv(serverSocket, router, config);
        jhttpsrv.run();
    }
}
