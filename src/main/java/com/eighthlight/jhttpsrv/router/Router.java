package com.eighthlight.jhttpsrv.router;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.handler.MethodNotAllowedRequestHandler;
import com.eighthlight.jhttpsrv.handler.NotFoundRequestHandler;
import com.eighthlight.jhttpsrv.handler.OptionsRequestHandler;
import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {
    private Map<Route, RequestHandler> routes;
    private Map<Pattern, String> options;

    public Router() {
        routes = new LinkedHashMap<Route, RequestHandler>();
        options = new LinkedHashMap<Pattern, String>();
    }

    public RequestHandler handlerByRoute(Request request) {
        if(ProtocolStrings.HTTP_METHOD_OPTIONS.equals(request.getMethod()))
            return new OptionsRequestHandler(options);

        for(Map.Entry<Route, RequestHandler> entry : routes.entrySet()) {
            Route route = entry.getKey();
            RequestHandler requestHandler = entry.getValue();

            if(route.matches(request))
                return requestHandler;
        }

        for(Map.Entry<Pattern, String> entry : options.entrySet()) {
            Pattern pattern = entry.getKey();
            Matcher matcher = pattern.matcher(request.getURLPath());

            if(matcher.matches())
                return new MethodNotAllowedRequestHandler();
        }

        return new NotFoundRequestHandler();
    }

    public void addRoute(String httpMethod, String url, RequestHandler requestHandler) {
        String quoted = Pattern.quote(url);
        addRegexRoute(httpMethod, quoted, requestHandler);
    }

    public void addRegexRoute(String httpMethod, String urlRegex, RequestHandler requestHandler) {
        Pattern pattern = Pattern.compile(urlRegex);
        Route route = new Route(httpMethod, pattern);
        routes.put(route, requestHandler);
        options.put(pattern, httpMethod);
    }
}