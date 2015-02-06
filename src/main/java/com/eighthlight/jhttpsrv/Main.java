package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.args.CmdArgs;
import com.eighthlight.jhttpsrv.config.Config;
import com.eighthlight.jhttpsrv.config.Setup;
import com.eighthlight.jhttpsrv.handler.*;
import com.eighthlight.jhttpsrv.logger.Logger;
import com.eighthlight.jhttpsrv.logger.MemoryLogger;
import com.eighthlight.jhttpsrv.parser.RequestParser;
import com.eighthlight.jhttpsrv.router.Router;
import com.eighthlight.jhttpsrv.server.Server;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;

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

        RequestParser parser = null;
        try {
            URL context = new URL(config.getOrigin());
            parser = new RequestParser(context);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Logger logger = new MemoryLogger();
        Router router = new Router();

        String redirectURL = String.format("http://localhost:%d/", config.getPort());
        DataHandler dataHandler = new DataHandler();
        FileRequestHandler fileRequestHandler;
        try {
            fileRequestHandler = new FileRequestHandler(config.getRootWWWDirectory());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/redirect", new RedirectRequestHandler(redirectURL));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", new TimeRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_POST, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_PUT, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_DELETE, "/form", dataHandler);
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/parameters", new EchoGETParamsHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_PATCH, "/patch-content.txt", new PatchHandler(fileRequestHandler));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/partial_content.txt", new RangeHandler(fileRequestHandler));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/logs", new BasicAuthHandler(new LogsRequestHandler(logger), "admin", "hunter2"));
        router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/method_options", new OKRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_HEAD, "/method_options", new OKRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_POST, "/method_options", new OKRequestHandler());
        router.addRoute(ProtocolStrings.HTTP_METHOD_PUT, "/method_options", new OKRequestHandler());
        router.addRegexRoute(ProtocolStrings.HTTP_METHOD_GET, "/.*", fileRequestHandler);

        Server server = new Server(serverSocket, router, logger, parser);
        server.run();
    }
}
