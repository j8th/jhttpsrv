package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.mocks.MockSocket;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETRequestChrome;
import com.eighthlight.jhttpsrv.testmessage.chrome.GETResponseChrome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class JhttpsrvTest {
    private Jhttpsrv jhttpsrv;

    @Before
    public void setUp() throws Exception {
        jhttpsrv = new Jhttpsrv();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void handle() throws Exception {
        MockSocket socket = new MockSocket();
        socket.seedInputStream(GETRequestChrome.EntireMessage);
        jhttpsrv.handle(socket);
        Assert.assertEquals(GETResponseChrome.ENTIRE_MESSAGE, socket.getOutputStreamAsString());
    }
}