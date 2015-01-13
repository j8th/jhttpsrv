package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class DataHandler implements RequestHandler {
    private Map<String, String> data = new HashMap<String, String>();

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();
        response.setHeaders(header);
        response.setBody(body);

        switch (request.getMethod()) {
            case ProtocolStrings.HTTP_METHOD_GET:
                String content = "";
                for(Map.Entry<String, String> entry : data.entrySet())
                    content += String.format("%s=%s", entry.getKey(), entry.getValue());
                body.setContent(content);
                break;

            case ProtocolStrings.HTTP_METHOD_POST:
                data = StringUtils.parseHttpFormData(request.getBody().getContent());
                break;

            case ProtocolStrings.HTTP_METHOD_PUT:
                Map<String, String> putData = StringUtils.parseHttpFormData(request.getBody().getContent());
                for(Map.Entry<String, String> entry : putData.entrySet())
                    data.put(entry.getKey(), entry.getValue());
                break;

            case ProtocolStrings.HTTP_METHOD_DELETE:
                data = new HashMap<String, String>();
                break;

            default:
                break;
        }

        response.setStatusCode(StatusCodes.OK);

        return response;
    }
}
