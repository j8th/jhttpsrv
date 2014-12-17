package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;

/**
 * Created by jason on 12/11/14.
 */
public class FormRequestHandler implements RequestHandler {
    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        body.setContent(
                "<html>\n" +
                        "<head>\n" +
                        "<title>A Form</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<form method=\"post\" action=\"/formsubmit\">\n" +
                        "<label for=\"name\">Your Name:</label>\n" +
                        "<input id=\"name\" type=\"text\">\n" +
                        "<button type=\"submit\">Submit</button>\n" +
                        "</form>\n" +
                        "</body>\n" +
                        "</html>\n"
        );

        header.setContentLength(body.getContentLength());
        header.setContentType(MIMETypes.HTML);

        response.setHeaders(header);
        response.setBody(body);
        return response;
    }
}
