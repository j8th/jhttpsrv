package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.args.CmdArgs;
import com.eighthlight.jhttpsrv.builder.ResponseBuilder;
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
import java.net.ServerSocket;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        CmdArgs cmdArgs = new CmdArgs(args);
        Setup setup = new Setup();
        try {
            setup.setFromCmdArgs(cmdArgs);
            Config config = setup.getConfig();
            URL context = new URL(config.getOrigin());

            ServerSocket serverSocket = new ServerSocket(config.getPort());
            Router router = new Router();
            Logger logger = new MemoryLogger();
            RequestParser parser = new RequestParser(context);
            ResponseBuilder builder = new ResponseBuilder();

            FileRequestHandler fileRequestHandler = new FileRequestHandler(config.getRootWWWDirectory());
            DataHandler dataHandler = new DataHandler();
            router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/redirect", new RedirectRequestHandler(config.getOrigin()));
            router.addRoute(ProtocolStrings.HTTP_METHOD_GET, "/time", new TimeRequestHandler(1000));
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

            Server server = new Server(System.out, serverSocket, router, logger, parser, builder);
            server.run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
