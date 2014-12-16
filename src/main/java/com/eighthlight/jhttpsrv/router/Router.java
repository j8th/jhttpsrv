package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.handler.OKRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jason on 12/10/14.
 */
public class Router {
    private Class<? extends RequestHandler> defaultHandler;
    private Map<String, Class> routes;

    public Router() {
        defaultHandler = OKRequestHandler.class;
        routes = new LinkedHashMap<String, Class>();
    }

    public RequestHandler route(Request request) {
        String requestRoute = String.format("%s %s", request.getMethod(), request.getURL());
        Class<? extends RequestHandler> handlerClass = routes.get(requestRoute);

        RequestHandler handler = null;
        try {
            if(handlerClass == null)
                handler = defaultHandler.newInstance();
            else
                handler = handlerClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return handler;
    }

    public void addRoute(String httpmethod, String url, Class<? extends RequestHandler> handlerClass) {
        String route = String.format("%s %s", httpmethod, url);
        routes.put(route, handlerClass);
    }
}
