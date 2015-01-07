package com.eighthlight.jhttpsrv.args;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CmdArgs {
    private static String defaultPort;
    private static String defaultDirectory;
    private static Map<String, String> tokens = new HashMap<String, String>();
    static {
        defaultPort = "8080";
        defaultDirectory = System.getProperty("user.dir") + "/www";
        Map<String, String> map = new HashMap<String, String>();

        map.put("--port", "port");
        map.put("-p", "port");
        map.put("--directory", "directory");
        map.put("-d", "directory");

        tokens = Collections.unmodifiableMap(map);
    }

    private String port;
    private String directory;

    public CmdArgs(String[] args) {
        Map<String, String> argsMap = new HashMap<String, String>();

        for(int i = 0; i < args.length; i++)
            if(tokens.containsKey(args[i]) && i+1 < args.length)
                argsMap.put(tokens.get(args[i]), args[i+1]);

        port = argsMap.getOrDefault("port", defaultPort);
        directory = argsMap.getOrDefault("directory", defaultDirectory);
    }

    public String getPort() {
        return port;
    }

    public String getDirectory() {
        return directory;
    }
}
