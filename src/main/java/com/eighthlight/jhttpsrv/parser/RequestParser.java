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

/**
 * Created by jason on 12/8/14.
 */
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

    // TODO: Throw some exceptions for when we exceed the buffer size.
    public Request parseInputStream(InputStream myis) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(myis);

        /*
         * Read all the bytes for the Request-Line and the Headers.
         * Parse them into handy objects.
         */
        byte[] requestLineBytes = getBytesUntilPattern(bis, ProtocolIntegers.END_OF_LINE);
        byte[] headerBytes = getBytesUntilPattern(bis, ProtocolIntegers.END_OF_HTTP_HEADER);

        String requestLineString = new String(requestLineBytes);
        String headerString = new String(headerBytes);

        Map<String, String> requestLineMap = parseRequestLine(requestLineString);
        Map<String, String> headerMap = parseHeaders(headerString);

        RequestHeader header = new RequestHeader(headerMap);
        RequestBody body = new RequestBody("");


        /*
         * Check the Headers for the presence of the Content-Length header field.
         * This field signifies that we have a Message Body in the HTTP Message.
         *
         * Reference:
         * http://tools.ietf.org/html/rfc2616#section-4.3
         */
        int bodyLength = header.getContentLength();
        if(bodyLength > 0) {
            byte[] bodyBytes = new byte[bodyLength];
            bis.read(bodyBytes, 0, bodyLength);
            String bodyString = new String(bodyBytes);
            body = new RequestBody(bodyString);
        }

        return new Request(requestLineMap, header, body);
    }





    /*
     * Private Methods
     */
    // TODO: Throw some exceptions for when we exceed the buffer size.
    /**
     * Reads an InputStream up until the supplied pattern of bytes is encountered and returns all the bytes read,
     * up to and including the provided pattern.
     * <p>
     * The InputStream's internal pointer is NOT reset to the beginning of the stream, but rather will point to
     * the final byte read.  So a subsequent call to read() on the InputStream will return the byte immediately after
     * the final byte of the provided pattern.
     *
     * @param myis The InputStream to be read.
     * @param bytePattern The pattern of bytes to look for.  Example:  {13, 10, 13, 10} would look for CRLFCRLF
     * @param max_bytes Maximum number of bytes to return.  If more than this many bytes are read, the returned result may be unpredictable.
     * @return A byte array comprised of all the bytes from the InputStream up until and including the provided pattern.
     */
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
            //if(bytes_read > max_bytes)
                //throw new Exception("Maximum number of bytes to be read exceeded.  Max: " + max_bytes);
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

    // TODO: Javadoc?
    private byte[] getBytesUntilPattern(BufferedInputStream myis, int[] bytePattern) throws IOException {
        return getBytesUntilPattern(myis, bytePattern, ProtocolIntegers.LIMIT_HEADER_FIELD_SIZE);
    }
}