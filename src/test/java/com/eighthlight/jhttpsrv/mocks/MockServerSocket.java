package com.eighthlight.jhttpsrv.mocks;

import com.eighthlight.jhttpsrv.testmessage.chrome.GETHelloworldRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {
    private boolean acceptCalledAtLeastOnce;

    public MockServerSocket() throws IOException {
        super();
        acceptCalledAtLeastOnce = false;
    }

    public Socket accept() {
        acceptCalledAtLeastOnce = true;
        MockSocket mockSocket = new MockSocket();
        mockSocket.seedInputStream(GETHelloworldRequest.ENTIRE_MESSAGE);
        return mockSocket;
    }

    public boolean acceptCalledAtLeastOnce() {
        return acceptCalledAtLeastOnce;
    }
}