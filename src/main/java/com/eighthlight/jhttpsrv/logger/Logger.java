package com.eighthlight.jhttpsrv.logger;

import java.util.List;

public interface Logger {
    public void log(String message);
    public List<String> getMessages();
}
