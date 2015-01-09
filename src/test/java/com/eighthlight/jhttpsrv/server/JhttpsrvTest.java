package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.mocks.MockServerSocket;
import com.eighthlight.jhttpsrv.router.Router;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class JhttpsrvTest {
    private MockServerSocket mockServerSocket;
    private Router router;
    private Jhttpsrv jhttpsrv;

    @Before
    public void setUp() throws Exception {
        mockServerSocket = new MockServerSocket();
        router = new Router();
        jhttpsrv = new Jhttpsrv(mockServerSocket, router);
    }

    @Test
    public void testRun() throws Exception {
        new Thread(jhttpsrv).start();
        Thread.sleep(500);
        assertTrue(mockServerSocket.acceptCalledAtLeastOnce());
    }
}