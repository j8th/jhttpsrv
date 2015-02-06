package com.eighthlight.jhttpsrv.config;

import com.eighthlight.jhttpsrv.args.CmdArgs;

public class Setup {
    private int port;
    private String rootWWWDirectory;

    public Setup() {
        port = 8080;
        rootWWWDirectory = System.getProperty("user.dir") + "/www";
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRootWWWDirectory(String rootWWWDirectory) {
        this.rootWWWDirectory = rootWWWDirectory;
    }

    public void setFromCmdArgs(CmdArgs cmdArgs) {
        if(!cmdArgs.getPort().equals(""))
            port = Integer.parseInt(cmdArgs.getPort());

        if(!cmdArgs.getDirectory().equals(""))
            rootWWWDirectory = cmdArgs.getDirectory();
    }

    public Config getConfig() {
        return new ConfigImplementation(port, rootWWWDirectory);
    }

    private class ConfigImplementation implements Config {
        private int port;
        private String rootWWWDirectory;

        public ConfigImplementation(int port, String rootWWWDirectory) {
            this.port = port;
            this.rootWWWDirectory = rootWWWDirectory;
        }

        public int getPort() {
            return port;
        }

        public String getRootWWWDirectory() {
            return rootWWWDirectory;
        }

        public String getOrigin() {
            return String.format("http://localhost:%d/", port);
        }
    }

}
