package com.eighthlight.jhttpsrv.logger;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MemoryLoggerTest {
    private MemoryLogger memoryLogger;

    @Before
    public void setUp() throws Exception {
        memoryLogger = new MemoryLogger();
    }

    @Test
    public void testLog() throws Exception {
        List<String> messages = memoryLogger.getMessages();
        assertTrue(messages.isEmpty());
        memoryLogger.log("Test message");
        memoryLogger.log("Test message 2");
        memoryLogger.log("Test message 3");
        messages = memoryLogger.getMessages();
        assertEquals("Test message", messages.get(0));
        assertEquals("Test message 2", messages.get(1));
        assertEquals("Test message 3", messages.get(2));
    }

    @Test
    public void testGetMessages() throws Exception {
        List<String> messages = memoryLogger.getMessages();
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testGetMessagesReturnsClone() throws Exception {
        List<String> first = memoryLogger.getMessages();
        assertTrue(first.isEmpty());
        first.add("Test message");
        List<String> second = memoryLogger.getMessages();
        assertTrue(second.isEmpty());
    }

    @Test
    public void testThreadSafety() throws Exception {
        int numWritingThreads = 1000;
        final int numMessagesPerThread = 10000;
        int totalExpectedLogMessages = numWritingThreads * numMessagesPerThread;
        Thread[] writers = new Thread[numWritingThreads];
        for(int i = 0; i < writers.length; i++) {
            final String msg = String.format("Thread #%d logging here.", i);
            writers[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < numMessagesPerThread; i++)
                        memoryLogger.log(msg);
                }
            });
        }

        for(int i = 0; i < writers.length; i++)
            writers[i].start();

        for(int i = 0; i < 100; i++)
            memoryLogger.getMessages();

        for(int i = 0; i < writers.length; i++)
            writers[i].join();

        List<String> messages = memoryLogger.getMessages();
        assertEquals(totalExpectedLogMessages, messages.size());
    }
}