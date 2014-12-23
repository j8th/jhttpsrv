package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.handler.FileRequestHandler;
import com.eighthlight.jhttpsrv.handler.OKRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;

import java.util.LinkedHashMap;
import java.util.Map;

public class Router {
    private RequestHandler defaultHandler;
    private Map<String, RequestHandler> routes;

    public Router() {
        defaultHandler = new OKRequestHandler();
        routes = new LinkedHashMap<String, RequestHandler>();
    }

    public RequestHandler handlerByRoute(Request request) {
        String requestRoute = buildRequestRoute(request.getMethod(), request.getURL());
        RequestHandler handler = routes.get(requestRoute);
        if(handler == null)
            return defaultHandler;
        return handler;
    }

    public void addRoute(String httpmethod, String url, RequestHandler myHandler) {
        String route = buildRequestRoute(httpmethod, url);
        routes.put(route, myHandler);
    }

    public void setDefaultRequestHandler(RequestHandler myRequestHandler) {
        defaultHandler = myRequestHandler;
    }

    private String buildRequestRoute(String HttpMethod, String Url) {
        return String.format("%s %s", HttpMethod, Url);
    }
}