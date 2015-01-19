package com.eighthlight.jhttpsrv.config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SetupTest {
    private Setup setup;
    private Config config;

    @Before
    public void setUp() throws Exception {
        setup = new Setup();
    }

    @Test
    public void testSetPort() throws Exception {
        setup.setPort(9090);
        config = setup.getConfig();

        assertEquals(9090, config.getPort());
    }

    @Test
    public void testSetWWWRootDirectory() throws Exception {
        String expectedDir = System.getProperty("user.dir") + "/www/dirtest";
        setup.setRootWWWDirectory(expectedDir);
        config = setup.getConfig();

        assertEquals(expectedDir, config.getRootWWWDirectory());
    }

    @Test
    public void testDefaults() {
        config = setup.getConfig();

        assertEquals(8080, config.getPort());
        assertEquals(System.getProperty("user.dir") + "/www", config.getRootWWWDirectory());
    }
}