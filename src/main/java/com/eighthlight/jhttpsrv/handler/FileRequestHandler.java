package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.handler.RequestHandler;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.shared.MIMETypes;
import com.eighthlight.jhttpsrv.shared.StatusCodes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jason on 12/11/14.
 */
public class FileRequestHandler implements RequestHandler {
    private static String rootdir = System.getProperty("user.dir") + "/www";



    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        // Set the body
        String path = rootdir + request.getURL();
        if(Files.isReadable(Paths.get(path))) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(path));
                String contents = new String(bytes, StandardCharsets.UTF_8);

                body.setContent(contents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatusCode(StatusCodes.NOT_FOUND);
        }

        // Set the headers
        header.setContentType(MIMETypes.HTML);
        header.setContentLength(body.getContentLength());

        // Put the response parts together
        response.setHeaders(header);
        response.setBody(body);

        return response;
    }



    /*
     * Static Methods
     */
    public static String getRootDir() {
        return rootdir;
    }

    public static void setRootDir(String absolutePath) {
        rootdir = absolutePath;
    }
}
