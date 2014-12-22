package com.eighthlight.jhttpsrv.parser;

import com.eighthlight.jhttpsrv.request.RequestBody;
import com.eighthlight.jhttpsrv.request.RequestHeader;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.shared.ProtocolIntegers;
import com.eighthlight.jhttpsrv.shared.ProtocolStrings;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    public static final String CRLF = "\r\n";

    public Map<String, String> parseRequestLine(String requestLine) {
        HashMap<String, String> result = new HashMap<String, String>();
        String[] tokens = requestLine.trim().split(" ");
        if(tokens.length < 3)
            return result;
        result.put(ProtocolStrings.METHOD, tokens[0]);
        result.put(ProtocolStrings.URL, tokens[1]);
        result.put(ProtocolStrings.PROTOCOL_VERSION, tokens[2].substring(tokens[2].indexOf('/') + 1));
        return result;
    }

    public Map<String, String> parseHeaders(String headers) {
        HashMap<String, String> result = new HashMap<String, String>();
        String[] headerLines = headers.split(CRLF);
        String[] tokens;
        String key;
        String val;
        for(int i=0; i < headerLines.length; i++) {
            tokens = headerLines[i].split(":");
            if(tokens.length == 2) {
                key = tokens[0];
                val = tokens[1].trim();
                result.put(key, val);
            }
        }
        return result;
    }

    public Request parseInputStream(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);

        byte[] requestLineBytes = getRequestLineBytes(bis);
        byte[] headerBytes = getHeaderBytes(bis);

        String requestLineString = new String(requestLineBytes);
        String headerString = new String(headerBytes);

        Map<String, String> requestLineMap = parseRequestLine(requestLineString);
        Map<String, String> headerMap = parseHeaders(headerString);

        RequestHeader header = new RequestHeader(headerMap);
        RequestBody body = new RequestBody("");

        int contentLength = header.getContentLength();
        if(contentLength > 0) {
            byte[] bodyBytes = new byte[contentLength];
            bis.read(bodyBytes, 0, contentLength);
            String bodyString = new String(bodyBytes);
            body = new RequestBody(bodyString);
        }

        return new Request(requestLineMap, header, body);
    }

    private byte[] getHeaderBytes(BufferedInputStream bufferedInputStream) throws IOException {
        return getBytesUntilPattern(bufferedInputStream, ProtocolIntegers.END_OF_HTTP_HEADER);
    }

    private byte[] getRequestLineBytes(BufferedInputStream bufferedInputStream) throws IOException {
        return getBytesUntilPattern(bufferedInputStream, ProtocolIntegers.END_OF_LINE);
    }

    private byte[] getBytesUntilPattern(BufferedInputStream myis, int[] bytePattern, int max_bytes) throws IOException {
        myis.mark(max_bytes);
        int i = 0;
        int current_byte = 0;
        int bytes_read = 0;

        while(i < bytePattern.length && current_byte != -1) {
            current_byte = myis.read();
            if(current_byte == -1)
                break;

            bytes_read++;
            if(current_byte == bytePattern[i])
                i++;
            else
                i = 0;
        }

        myis.reset();
        byte[] result = new byte[bytes_read];
        myis.read(result, 0, bytes_read);
        return result;
    }

    private byte[] getBytesUntilPattern(BufferedInputStream myis, int[] bytePattern) throws IOException {
        return getBytesUntilPattern(myis, bytePattern, ProtocolIntegers.LIMIT_HEADER_FIELD_SIZE);
    }
}