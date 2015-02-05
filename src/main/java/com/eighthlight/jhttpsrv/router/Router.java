package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.handler.OKRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Router {
    private RequestHandler defaultHandler;
    private Map<Route, RequestHandler> routes;

    public Router() {
        defaultHandler = new OKRequestHandler();
        routes = new LinkedHashMap<Route, RequestHandler>();
    }

    public RequestHandler handlerByRoute(Request request) {
        for(Map.Entry<Route, RequestHandler> entry : routes.entrySet()) {
            Route route = entry.getKey();
            RequestHandler requestHandler = entry.getValue();

            if(route.matches(request))
                return requestHandler;
        }

        return defaultHandler;
    }

    public void addRoute(String httpMethod, String url, RequestHandler requestHandler) {
        String quoted = Pattern.quote(url);
        addRegexRoute(httpMethod, quoted, requestHandler);
    }

    public void addRegexRoute(String httpMethod, String urlRegex, RequestHandler requestHandler) {
        Pattern pattern = Pattern.compile(urlRegex);
        Route route = new Route(httpMethod, pattern);
        routes.put(route, requestHandler);
    }

    public void setDefaultRequestHandler(RequestHandler requestHandler) {
        defaultHandler = requestHandler;
    }
}