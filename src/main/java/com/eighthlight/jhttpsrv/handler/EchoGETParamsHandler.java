package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EchoGETParamsHandler implements RequestHandler {
    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();
        response.setHeaders(header);
        response.setBody(body);

        Map<String, String> getParams = new HashMap<String, String>();
        String query = request.getURLQuery();
        String[] tokens = query.split("&");
        String[] pair;
        for(int i = 0; i < tokens.length; i++) {
            pair = tokens[i].split("=");
            if(pair.length == 2) {
                try {
                    getParams.put(pair[0], URLDecoder.decode(pair[1], StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        String content = "";
        for(Map.Entry<String, String> entry : getParams.entrySet())
            content += String.format("%s = %s", entry.getKey(), entry.getValue());

        body.setContent(content);

        response.setStatusCode(StatusCodes.OK);

        return response;
    }
}
