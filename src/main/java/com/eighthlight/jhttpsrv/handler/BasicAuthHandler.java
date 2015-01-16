package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.request.RequestHeader;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthHandler implements RequestHandler {
    private RequestHandler requestHandler;
    private String username;
    private String password;

    public BasicAuthHandler(RequestHandler requestHandler, String username, String password) {
        this.requestHandler = requestHandler;
        this.username = username;
        this.password = password;
    }

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();
        response.setHeaders(header);
        response.setBody(body);

        String submittedCreds = getCreds(request.getHeader());
        String properCreds = makeCreds(username, password);

        if(submittedCreds != null && submittedCreds.equals(properCreds)) {
            response = requestHandler.run(request);
        } else {
            response.setStatusCode(StatusCodes.UNAUTHORIZED);
            body.setContent("Authentication required\n");
        }


        return response;
    }

    private String getCreds(RequestHeader header) {
        String[] tokens = header.getAuthorization().split(" ");
        if(tokens.length == 2)
            return tokens[1];
        return null;
    }

    private String makeCreds(String username, String password) {
        Base64.Encoder encoder = Base64.getEncoder();
        String creds = String.format("%s:%s", username, password);
        byte[] bytes = creds.getBytes(StandardCharsets.UTF_8);
        return encoder.encodeToString(bytes);
    }
}
