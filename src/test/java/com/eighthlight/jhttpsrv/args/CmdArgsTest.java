package com.eighthlight.jhttpsrv.args;

import org.junit.Test;

import static org.junit.Assert.*;

public class CmdArgsTest {
    @Test
    public void testGetPort() {
        String[] goodArgs = {"--port", "80"};
        CmdArgs cmdArgs = new CmdArgs(goodArgs);

        assertEquals("80", cmdArgs.getPort());
    }

    @Test
    public void testGetPortShortFlag() {
        String[] goodArgs = {"-p", "9090"};
        CmdArgs cmdArgs = new CmdArgs(goodArgs);

        assertEquals("9090", cmdArgs.getPort());
    }

    @Test
    public void testGetPortDefault() {
        String[] emptyArgs = {};
        CmdArgs cmdArgs = new CmdArgs(emptyArgs);

        assertEquals("", cmdArgs.getPort());
    }

    @Test
    public void testGetPortGarbageArgument() {
        String[] badArgs = {"-p", "NotValid"};
        CmdArgs cmdArgs = new CmdArgs(badArgs);

        assertEquals("NotValid", cmdArgs.getPort());
    }

    @Test
    public void testGetPortLastArgWins() {
        String[] badArgs = {"--port", "9090", "-d", "/path/to/some/dir", "-p", "900"};
        CmdArgs cmdArgs = new CmdArgs(badArgs);

        assertEquals("900", cmdArgs.getPort());
    }

    @Test
    public void testGetDirectory() {
        String[] goodArgs = {"--directory", "/path/to/some/dir"};
        CmdArgs cmdArgs = new CmdArgs(goodArgs);

        assertEquals("/path/to/some/dir", cmdArgs.getDirectory());
    }

    @Test
    public void testGetDirectoryDefault() {
        String[] emptyArgs = {};
        CmdArgs cmdArgs = new CmdArgs(emptyArgs);

        assertEquals("", cmdArgs.getDirectory());
    }

    @Test
    public void testGetDirectoryGarbage() {
        String[] garbageArgs = {"--blah", "nothing here hi", "--directory", "laknfen iii 333"};
        CmdArgs cmdArgs = new CmdArgs(garbageArgs);

        assertEquals("laknfen iii 333", cmdArgs.getDirectory());
    }

    @Test
    public void testGetDirectoryNoParameter() {
        String[] missingParamArgs = {"--directory"};
        CmdArgs cmdArgs = new CmdArgs(missingParamArgs);

        assertEquals("", cmdArgs.getDirectory());
    }

    @Test
    public void testFoolishArgumentsAreAccepted() {
        String[] foolishArgs = {"--blah", "nothing here hi", "--directory", "--port", "65,000"};
        CmdArgs cmdArgs = new CmdArgs(foolishArgs);

        assertEquals("65,000", cmdArgs.getPort());
        assertEquals("--port", cmdArgs.getDirectory());
    }

    @Test
    public void testSettingPortAndDirectorySimultaneouslyWorks() {
        String[] goodArgs = {"--directory", "/var/www", "-p", "80"};
        CmdArgs cmdArgs = new CmdArgs(goodArgs);

        assertEquals("/var/www", cmdArgs.getDirectory());
        assertEquals("80", cmdArgs.getPort());
    }
}