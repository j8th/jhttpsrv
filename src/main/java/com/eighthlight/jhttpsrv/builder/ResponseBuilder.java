package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.ProtocolIntegers;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * Created by jason on 12/10/14.
 */
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

        for(String headerKey : ProtocolStrings.RESPONSE_HEADER_KEYS) {
            String methodName = String.format("get%s", headerKey.replaceAll("-", ""));
            try {
                Method method = header.getClass().getMethod(methodName);
                String headerVal = "" + method.invoke(header);
                if(headerVal.length() > 0)
                    result += String.format("%s: %s\r\n", headerKey, headerVal);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
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
}
