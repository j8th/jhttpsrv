package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.MIMETypes;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.request.RequestBody;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.util.StringUtils;

import java.util.Map;

public class FormRequestHandler implements RequestHandler {
    private final String bodyStringTemplate =
            "<html>\n" +
              "<head>\n" +
                "<title>Hello World</title>\n" +
              "</head>\n" +
              "<body>\n" +
                "<p>Submitted Form Data:</p>\n" +
                "<table>" +
                  "<tr>" +
                    "<td>" +
                      "Key" +
                    "</td>" +
                    "<td>" +
                      "Value" +
                    "</td>" +
                  "</tr>" +
                  "%s" +
                "</table>" +
              "</body>\n" +
            "</html>\n";

    private final String itemTemplate =
            "<tr>" +
              "<td>%s</td>" +
              "<td>%s</td>" +
            "</tr>";


    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        response.setStatusCode(StatusCodes.OK);
        header.setContentType(MIMETypes.HTML);

        String tableContent = "";
        Map<String, String> formData = StringUtils.parseHttpFormData(request.getBody().getContent());
        for(Map.Entry<String, String> field : formData.entrySet()) {
            String key = field.getKey();
            String val = field.getValue();
            tableContent += String.format(itemTemplate, key, val);
        }

        String content = String.format(bodyStringTemplate, tableContent);

        body.setContent(content);

        response.setHeaders(header);
        response.setBody(body);
        return response;
    }
}
