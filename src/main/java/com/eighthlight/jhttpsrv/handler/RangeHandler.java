package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.request.RequestHeader;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.util.ArrayUtils;

public class RangeHandler implements RequestHandler {
    private RequestHandler requestHandler;

    public RangeHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public Response run(Request request) {
        Response response = requestHandler.run(request);

        if(response.getStatusCode() == StatusCodes.OK) {
            response.setStatusCode(StatusCodes.PARTIAL_CONTENT);

            Integer start = getStartRange(request.getHeader());
            Integer end = getEndRange(request.getHeader());

            byte[] partialContent;
            if(start == null && end != null)
                partialContent = ArrayUtils.getArrayTail(response.getBody().getContent(), end);
            else
                partialContent = ArrayUtils.getArrayRange(response.getBody().getContent(), start, end);

            response.getBody().setContent(partialContent);
        }

        return response;
    }

    private Integer getStartRange(RequestHeader requestHeader) {
        Integer[] range = getByteRange(requestHeader);
        return range[0];
    }

    private Integer getEndRange(RequestHeader requestHeader) {
        Integer[] range = getByteRange(requestHeader);
        return range[1];
    }

    private Integer[] getByteRange(RequestHeader requestHeader) {
        Integer start = null;
        Integer end = null;
        String[] byteRanges = getByteRanges(requestHeader);

        if(byteRanges.length > 0) {
            String firstRange = byteRanges[0];
            String[] tokens = firstRange.split("-");

            if(tokens.length >= 1) {
                try {
                    start = Integer.parseInt(tokens[0]);
                } catch(NumberFormatException e) {
                    start = null;
                }
            }

            if(tokens.length == 2) {
                try {
                    end = Integer.parseInt(tokens[1]);
                } catch(NumberFormatException e) {
                    end = null;
                }
            }
        }

        return new Integer[] {start, end};
    }

    private String[] getByteRanges(RequestHeader requestHeader) {
        String rangeHeader = requestHeader.getRange();

        String[] byteRanges = new String[0];
        String[] tokens = rangeHeader.split("=");
        if (tokens.length == 2)
            byteRanges = tokens[1].split(",");
        return byteRanges;
    }
}
