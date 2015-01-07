package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.response.ResponseBody;
import com.eighthlight.jhttpsrv.response.ResponseHeader;
import com.eighthlight.jhttpsrv.constants.MIMETypes;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileRequestHandler implements RequestHandler {
    private static String rootdir = System.getProperty("user.dir") + "/www";
    private static final String dirListingContentTemplate =
            "<html>" +
              "<head>" +
                "<title>Directory Listing</title>" +
              "</head>" +
              "<body>" +
                "%s" +
              "</body>" +
            "</html>";
    private static final String dirListingEntryTemplate = 
            "<p><a href=\"%s\">%s</a></p>";

    public Response run(Request request) {
        Response response = new Response();
        ResponseHeader header = new ResponseHeader();
        ResponseBody body = new ResponseBody();

        Path abspath = Paths.get(rootdir + request.getURL());
        String ext = StringUtils.getFileExtension(rootdir + request.getURL());
        if(Files.isReadable(abspath)) {
            if(Files.isDirectory(abspath)) {
                String content = createDirectoryListingHtml(abspath);

                header.setContentType(MIMETypes.HTML);
                body.setContent(content);
                response.setStatusCode(StatusCodes.OK);
            } else {
                try {
                    byte[] bytes = Files.readAllBytes(abspath);
                    String contentType = MIMETypes.FileExt2MIMEType(ext);

                    body.setContent(bytes);
                    header.setContentType(contentType);
                    header.setContentLength(body.getContentLength());
                    response.setStatusCode(StatusCodes.OK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private String getPathRelativeToRootDir(File file) {
        String path = file.getAbsolutePath();
        if(file.isDirectory())
            path += "/";
        return path.replace(getRootDir(), "");
    }

    private String createDirectoryListingHtml(Path abspath) {
        File dir = new File(abspath.toString());
        File[] files = dir.listFiles();

        String entries = "";
        for(int i = 0; i < files.length; i++) {
            File myfile = files[i];
            String url = getPathRelativeToRootDir(myfile);
            String text = myfile.getName();
            if(myfile.isDirectory())
                text += "/";
            entries += String.format(dirListingEntryTemplate, url, text);
        }

        File parent = dir.getParentFile();
        String url = getPathRelativeToRootDir(parent);
        String text = "^";
        entries += String.format(dirListingEntryTemplate, url, text);

        return String.format(dirListingContentTemplate, entries);
    }
}
