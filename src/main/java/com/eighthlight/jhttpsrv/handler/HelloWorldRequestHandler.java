package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

/**
 * Created by jason on 12/11/14.
 */
public class HelloWorldRequestHandler implements RequestHandler {
    private String bodyString =
            "<html>\n" +
            "<head>\n" +
            "<title>Hello World</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Hello World</h1>\n" +
            "</body>\n" +
            "</html>\n";



    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        body.setContent(bodyString);

        header.setContentType(MIMETypes.HTML);
        header.setContentLength(body.getContentLength());

        response.setStatusCode(StatusCodes.OK);
        response.setHeaders(header);
        response.setBody(body);

        return response;
    }
}
