package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptionsRequestHandler implements RequestHandler {
    private Map<Pattern, String> options;

    public OptionsRequestHandler(Map<Pattern, String> options) {
        this.options = options;
    }

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = response.getHeaders();

        String urlPath = request.getURLPath();
        Set<String> methods = new HashSet<String>();

        for(Map.Entry<Pattern, String> entry : options.entrySet()) {
            Pattern pattern = entry.getKey();
            String method = entry.getValue();
            Matcher matcher = pattern.matcher(urlPath);

            if(matcher.matches())
                methods.add(method);
        }

        methods.add(ProtocolStrings.HTTP_METHOD_OPTIONS);

        response.setStatusCode(StatusCodes.OK);
        header.setAllow(methods.toArray(new String[methods.size()]));

        return response;
    }
}
