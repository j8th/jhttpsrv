package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.handler.OKRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;

import java.util.LinkedHashMap;
import java.util.Map;

public class Router {
    private Class<? extends RequestHandler> defaultHandler;
    private Map<String, Class<? extends RequestHandler>> routes;

    public Router() {
        defaultHandler = OKRequestHandler.class;
        routes = new LinkedHashMap<String, Class<? extends RequestHandler> >();
    }

    public RequestHandler handlerByRoute(Request request) {
        String requestRoute = buildRequestRoute(request.getMethod(), request.getURL());
        Class<? extends RequestHandler> handlerClass = routes.get(requestRoute);

        RequestHandler handler = null;
        try {
            if(handlerClass == null)
                handler = defaultHandler.newInstance();
            else
                handler = handlerClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return handler;
    }

    public void addRoute(String httpmethod, String url, Class<? extends RequestHandler> handlerClass) {
        String route = buildRequestRoute(httpmethod, url);
        routes.put(route, handlerClass);
    }

    public void setDefaultRouteHandler(Class<? extends RequestHandler> myClass) {
        defaultHandler = myClass;
    }

    private String buildRequestRoute(String HttpMethod, String Url) {
        return String.format("%s %s", HttpMethod, Url);
    }
}