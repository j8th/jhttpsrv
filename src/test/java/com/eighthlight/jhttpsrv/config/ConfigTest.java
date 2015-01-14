package com.eighthlight.jhttpsrv.config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {
    private Config config;

    @Before
    public void setUp() throws Exception {
        config = new Config(8080);
    }

    @Test
    public void testGetPort() {
        assertEquals(8080, config.getPort());
    }
}