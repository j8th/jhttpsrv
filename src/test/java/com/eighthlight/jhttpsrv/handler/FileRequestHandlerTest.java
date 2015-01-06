package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.constants.MIMETypes;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileRequestHandlerTest {
    private FileRequestHandler fileRequestHandler;
    private Request request;

    @Before
    public void setUp() throws Exception {
        fileRequestHandler = new FileRequestHandler();
        request = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);
    }

    @Test
    public void testRun() throws Exception {
        Response response = fileRequestHandler.run(request);

        String FilePath = FileRequestHandler.getRootDir() + "/index.html";
        byte[] bytes = Files.readAllBytes(Paths.get(FilePath));

        Assert.assertEquals(MIMETypes.HTML, response.getHeaders().getContentType());
        Assert.assertArrayEquals(bytes, response.getBody().getContent());
    }

    @Test
    public void testPlainText() throws Exception {
        Request request = TestRequestMaker.fromString(GETtextfileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        Assert.assertEquals(MIMETypes.PLAIN_TEXT, response.getHeaders().getContentType());
    }

    @Test
    public void testImage() throws Exception {
        Request request = TestRequestMaker.fromString(GETimageRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);
        byte[] fileBytes = getTestFileBytes("/images/testimg.jpeg");

        Assert.assertEquals(MIMETypes.JPEG, response.getHeaders().getContentType());
        Assert.assertArrayEquals(fileBytes, response.getBody().getContent());
    }

    @Test
    public void testGzipFile() throws Exception {
        Request request = TestRequestMaker.fromString(GETgzipFileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);
        byte[] fileBytes = getTestFileBytes("/download/testdl.txt.gz");

        Assert.assertEquals(MIMETypes.GZIP, response.getHeaders().getContentType());
        Assert.assertArrayEquals(fileBytes, response.getBody().getContent());
        Assert.assertEquals(StatusCodes.OK, response.getStatusCode());
    }

    @Test
    public void testJsonFile() throws Exception {
        Request request = TestRequestMaker.fromString(GETjsonFileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);
        byte[] fileBytes = getTestFileBytes("/test.json");

        Assert.assertEquals(MIMETypes.JSON, response.getHeaders().getContentType());
        Assert.assertArrayEquals(fileBytes, response.getBody().getContent());
        Assert.assertEquals(StatusCodes.OK, response.getStatusCode());
    }

    @Test
    public void testRequestedFileUnreadable() throws Exception {
        Request request = TestRequestMaker.fromString(GET404Request.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        Assert.assertEquals(StatusCodes.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRequestingDirectoryGives404() throws Exception {
        Request request = TestRequestMaker.fromString(GETDirectoryRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        Assert.assertEquals(StatusCodes.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDefaultRootDirectory() throws Exception {
        Assert.assertEquals(System.getProperty("user.dir") + "/www", FileRequestHandler.getRootDir());
    }

    @Test
    public void testSetRootDirectory() throws Exception {
        FileRequestHandler.setRootDir("/path/to/some/dir");
        Assert.assertEquals("/path/to/some/dir", FileRequestHandler.getRootDir());
    }

    private byte[] getTestFileBytes(String testFilePathRelativeToWWWRoot) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/www" + testFilePathRelativeToWWWRoot));
    }
}