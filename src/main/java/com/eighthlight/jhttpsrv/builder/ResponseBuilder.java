package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.constants.ProtocolIntegers;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseBuilder {
    public byte[] buildStatusLine(Response response) {
        String result = String.format("HTTP/%s %s %s\r\n",
                response.getProtocolVersion(),
                response.getStatusCode(),
                response.getReasonPhrase());

        return result.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] buildHeaders(Response response) {
        String result = "";
        ResponseHeader header = response.getHeaders();

        result += buildHeaderString(ProtocolStrings.CONTENT_LENGTH, header.getContentLength());
        result += buildHeaderString(ProtocolStrings.CONTENT_TYPE, header.getContentType());
        result += buildHeaderString(ProtocolStrings.LOCATION, header.getLocation());

        return result.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] buildBody(Response response) {
        ResponseBody body = response.getBody();
        return body.getContent();
    }

    public byte[] buildResponse(Response response) {
        byte[] statusLineBytes = buildStatusLine(response);
        byte[] headerBytes = buildHeaders(response);
        byte[] emptyLine = new byte[] {ProtocolIntegers.CR, ProtocolIntegers.LF};
        byte[] bodyBytes = buildBody(response);

        ByteArrayOutputStream myos = new ByteArrayOutputStream();
        try {
            myos.write(statusLineBytes);
            myos.write(headerBytes);
            myos.write(emptyLine);
            myos.write(bodyBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }

        return myos.toByteArray();
    }

    private String buildHeaderString(String headerKey, String headerVal) {
        if(headerVal != "")
            return String.format("%s: %s\r\n", headerKey, headerVal);
        return "";
    }

    private String buildHeaderString(String headerKey, int headerVal) {
        if(headerVal > 0)
            return String.format("%s: %s\r\n", headerKey, headerVal);
        return "";
    }
}
