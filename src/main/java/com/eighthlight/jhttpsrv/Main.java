package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.args.CmdArgs;
import com.eighthlight.jhttpsrv.config.Config;
import com.eighthlight.jhttpsrv.config.Setup;
import com.eighthlight.jhttpsrv.handler.*;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.logger.MemoryLogger;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.server.Jhttpsrv;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        CmdArgs cmdArgs = new CmdArgs(args);

        Setup setup = new Setup();
        if(!cmdArgs.getPort().equals(""))
            try {
                setup.setPort(Integer.parseInt(cmdArgs.getPort()));
            } catch (NumberFormatException e) {
                System.err.println("Supplied port must be an integer.");
                System.exit(1);
                return;
            }
        if(!cmdArgs.getDirectory().equals(""))
            setup.setRootWWWDirectory(cmdArgs.getDirectory());

        Config config = setup.getConfig();

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(config.getPort());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        FileRequestHandler fileRequestHandler;
        try {
            fileRequestHandler = new FileRequestHandler(config.getRootWWWDirectory());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        Logger logger = new MemoryLogger();

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
        router.addRoute(ProtocolStrings.HTTP_METHOD_PATCH, "/patch-content.txt", new PatchHandler(fileRequestHandler));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/partial_content.txt", new RangeHandler(fileRequestHandler));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/logs", new BasicAuthHandler(new LogsRequestHandler(logger), "admin", "hunter2"));
        router.setDefaultRequestHandler(fileRequestHandler);

        Jhttpsrv jhttpsrv = new Jhttpsrv(serverSocket, router, logger, config);
        jhttpsrv.run();
    }
}
