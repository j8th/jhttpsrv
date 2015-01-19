package com.eighthlight.jhttpsrv.logger;

import java.util.ArrayList;
import java.util.List;

public class MemoryLogger implements Logger {
    private List<String> messages;

    public MemoryLogger() {
        messages = new ArrayList<String>();
    }

    public synchronized void log(String message) {
        messages.add(message);
    }

    public synchronized List<String> getMessages() {
        List<String> clone = new ArrayList<String>(messages.size());
        for(String msg : messages)
            clone.add(msg);
        return clone;
    }
}
