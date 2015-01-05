package com.eighthlight.jhttpsrv.server;

import com.eighthlight.jhttpsrv.router.Router;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.ServerSocket;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;

public class JhttpsrvTest {
    private ServerSocket mockServerSocket;
    private Router router;
    private Jhttpsrv jhttpsrv;

    @Before
    public void setUp() throws Exception {
        mockServerSocket = mock(ServerSocket.class);
        router = new Router();
        jhttpsrv = new Jhttpsrv(mockServerSocket, router);
    }

    @Test
    public void testRun() throws Exception {
        new Thread(jhttpsrv).start();
        Mockito.verify(mockServerSocket, atLeastOnce()).accept();
    }
}