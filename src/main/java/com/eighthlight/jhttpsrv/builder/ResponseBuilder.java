package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.constants.ProtocolIntegers;
import com.eighthlight.jhttpsrv.constants.ProtocolStrings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ResponseBuilder {
    public byte[] buildStatusLine(Response response) {
        String result = String.format("HTTP/%s %s %s\r\n",
                response.getProtocolVersion(),
                response.getStatusCode(),
                response.getReasonPhrase());

        return result.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] buildHeaders(ResponseHeader header) {
        String result = "";

        result += buildHeaderString(ProtocolStrings.CONTENT_LENGTH, header.getContentLength());
        result += buildHeaderString(ProtocolStrings.CONTENT_TYPE, header.getContentType());
        result += buildHeaderString(ProtocolStrings.LOCATION, header.getLocation());
        result += buildHeaderString(ProtocolStrings.ALLOW, header.getAllow());

        return result.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] buildBody(ResponseBody body) {
        return body.getContent();
    }

    public byte[] buildResponse(Response response) {
        byte[] statusLineBytes = buildStatusLine(response);
        byte[] headerBytes = buildHeaders(response.getHeaders());
        byte[] emptyLine = new byte[] {ProtocolIntegers.CR, ProtocolIntegers.LF};
        byte[] bodyBytes = buildBody(response.getBody());

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

    private String buildHeaderString(String headerKey, String[] headerVal) {
        if(headerVal.length > 0)
            return String.format("%s: %s\r\n", headerKey, String.join(",", Arrays.asList(headerVal)));
        return "";
    }
}
