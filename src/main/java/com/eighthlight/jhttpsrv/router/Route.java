package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.request.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private String method;
    private Pattern pattern;

    public Route(String method, Pattern pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    public boolean matches(Request request) {
        Matcher matcher = pattern.matcher(request.getURLPath());
        if(method.equals(request.getMethod()) && matcher.matches())
            return true;
        return false;
    }
}
