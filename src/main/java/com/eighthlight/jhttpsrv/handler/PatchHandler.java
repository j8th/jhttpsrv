package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PatchHandler implements RequestHandler {
    private FileRequestHandler fileRequestHandler;

    public PatchHandler(FileRequestHandler fileRequestHandler) {
        this.fileRequestHandler = fileRequestHandler;
    }

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();
        response.setHeaders(header);
        response.setBody(body);

        if(request.getMethod().equals(ProtocolStrings.HTTP_METHOD_PATCH)) {
            Path abspath = Paths.get(FileRequestHandler.getRootDir() + request.getURLPath());
            if(Files.isReadable(abspath) && Files.isWritable(abspath)) {
                try {
                    FileOutputStream out = new FileOutputStream(abspath.toString());
                    out.write(request.getBody().getContent().getBytes(StandardCharsets.UTF_8));
                    out.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            } else {
                response = fileRequestHandler.run(request);
            }
            response.setStatusCode(StatusCodes.NO_CONTENT);
        } else {
            response = fileRequestHandler.run(request);
        }

        return response;
    }
}
