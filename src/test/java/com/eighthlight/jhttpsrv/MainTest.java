package com.eighthlight.jhttpsrv;

import com.eighthlight.jhttpsrv.server.Jhttpsrv;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.net.ServerSocket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MainTest {
    private Jhttpsrv jhttpsrv;

    @Before
    public void setUp() throws Exception {
        ServerSocket mockServerSocket = mock(ServerSocket.class);
        Main.init(mockServerSocket);

        jhttpsrv = Main.getJhttpsrv();
    }
}