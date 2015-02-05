package com.eighthlight.jhttpsrv.router;

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
        String requestRoute = buildRequestRoute(request.getMethod(), request.getURLPath());
        RequestHandler handler = routes.get(requestRoute);
        if(handler == null)
            return defaultHandler;
        return handler;
    }

    public void addRoute(String httpMethod, String url, RequestHandler requestHandler) {
        String route = buildRequestRoute(httpMethod, url);
        routes.put(route, requestHandler);
    }

    public void setDefaultRequestHandler(RequestHandler requestHandler) {
        defaultHandler = requestHandler;
    }

    private String buildRequestRoute(String httpMethod, String url) {
        return String.format("%s %s", httpMethod, url);
    }
}