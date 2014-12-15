package com.eighthlight.jhttpsrv.builder;

import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jason on 12/10/14.
 */
public class ResponseBuilder {
    public String buildStatusLine(Response response) {
        String result = String.format("HTTP/%s %s %s\r\n",
                response.getProtocolVersion(),
                response.getStatusCode(),
                response.getReasonPhrase());

        return result;
    }

    public String buildHeaders(Response response) {
        String result = "";
        ResponseHeader header = response.getHeaders();

        for(String headerKey : ProtocolStrings.RESPONSE_HEADER_KEYS) {
            String methodName = String.format("get%s", headerKey.replaceAll("-", ""));
            try {
                Method method = header.getClass().getMethod(methodName);
                String headerVal = "" + method.invoke(header);
                if(headerKey.length() > 0)
                    result += String.format("%s: %s\r\n", headerKey, headerVal);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String buildBody(Response response) {
        ResponseBody body = response.getBody();
        return body.getContent();
    }

    public String buildResponse(Response response) {
        return buildStatusLine(response) + buildHeaders(response) + "\r\n" + buildBody(response);
    }
}
