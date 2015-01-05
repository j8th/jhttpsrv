package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;
import com.eighthlight.jhttpsrv.shared.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileRequestHandler implements RequestHandler {
    private static String rootdir = System.getProperty("user.dir") + "/www";

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        Path abspath = Paths.get(rootdir + request.getURL());
        String ext = StringUtils.getFileExtension(rootdir + request.getURL());
        if(Files.isReadable(abspath) && !Files.isDirectory(abspath)) {
            try {
                byte[] bytes = Files.readAllBytes(abspath);
                String contentType = MIMETypes.FileExt2MIMEType(ext);
                if(contentType == MIMETypes.OCTET_STREAM)
                    contentType = MIMETypes.HTML;

                body.setContent(bytes);
                header.setContentType(contentType);
                header.setContentLength(body.getContentLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatusCode(StatusCodes.NOT_FOUND);
        }

        response.setHeaders(header);
        response.setBody(body);

        return response;
    }

    public static String getRootDir() {
        return rootdir;
    }

    public static void setRootDir(String absolutePath) {
        rootdir = absolutePath;
    }
}