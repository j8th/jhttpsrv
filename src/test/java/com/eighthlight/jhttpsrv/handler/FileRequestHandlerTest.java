package com.eighthlight.jhttpsrv.handler;

import com.eighthlight.jhttpsrv.constants.ProtocolStrings;
import com.eighthlight.jhttpsrv.request.Request;
import com.eighthlight.jhttpsrv.response.Response;
import com.eighthlight.jhttpsrv.constants.MIMETypes;
import com.eighthlight.jhttpsrv.constants.StatusCodes;
import com.eighthlight.jhttpsrv.testmessage.chrome.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;

public class FileRequestHandlerTest {
    private FileRequestHandler fileRequestHandler;
    private Request request;
    private String rootDir = System.getProperty("user.dir") + "/www";

    @Before
    public void setUp() throws Exception {
        fileRequestHandler = new FileRequestHandler(rootDir);
        request = TestRequestMaker.fromString(GETindexhtmlRequest.ENTIRE_MESSAGE);
    }

    @Test
    public void testRun() throws Exception {
        Response response = fileRequestHandler.run(request);

        String FilePath = fileRequestHandler.getRootDir() + "/index.html";
        byte[] bytes = Files.readAllBytes(Paths.get(FilePath));

        assertEquals(MIMETypes.HTML, response.getHeaders().getContentType());
        assertArrayEquals(bytes, response.getBody().getContent());
    }

    @Test
    public void testPlainText() throws Exception {
        Request request = TestRequestMaker.fromString(GETtextfileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        assertEquals(MIMETypes.PLAIN_TEXT, response.getHeaders().getContentType());
    }

    @Test
    public void testImage() throws Exception {
        Request request = TestRequestMaker.fromString(GETimageRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);
        byte[] fileBytes = getTestFileBytes("/images/testimg.jpeg");

        assertEquals(MIMETypes.JPEG, response.getHeaders().getContentType());
        assertArrayEquals(fileBytes, response.getBody().getContent());
    }

    @Test
    public void testGzipFile() throws Exception {
        Request request = TestRequestMaker.fromString(GETgzipFileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);
        byte[] fileBytes = getTestFileBytes("/download/testdl.txt.gz");

        assertEquals(MIMETypes.GZIP, response.getHeaders().getContentType());
        assertArrayEquals(fileBytes, response.getBody().getContent());
        assertEquals(StatusCodes.OK, response.getStatusCode());
    }

    @Test
    public void testJsonFile() throws Exception {
        Request request = TestRequestMaker.fromString(GETjsonFileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);
        byte[] fileBytes = getTestFileBytes("/test.json");

        assertEquals(MIMETypes.JSON, response.getHeaders().getContentType());
        assertArrayEquals(fileBytes, response.getBody().getContent());
        assertEquals(StatusCodes.OK, response.getStatusCode());
    }

    @Test
    public void testDirectoryRequestReturnsHTMLListing() throws Exception {
        Request request = TestRequestMaker.fromString(GETDirectoryRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        assertEquals(StatusCodes.OK, response.getStatusCode());
        assertEquals(MIMETypes.HTML, response.getHeaders().getContentType());
        String content = new String(response.getBody().getContent(), StandardCharsets.UTF_8);
        assertThat(content, containsString("<a href=\"/dirtest/candy/\">candy/</a>"));
        assertThat(content, containsString("<a href=\"/dirtest/hello.html\">hello.html</a>"));
        assertThat(content, containsString("<a href=\"/\">^</a>"));
    }

    @Test
    public void testRequestedFileUnreadable() throws Exception {
        Request request = TestRequestMaker.fromString(GET404Request.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        assertEquals(StatusCodes.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testPOSTRequestToExistingFileReturns405() throws Exception {
        Request request = TestRequestMaker.fromString(POSTtextfileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        assertEquals(StatusCodes.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    public void testPUTRequestToExistingFileReturns405() throws Exception {
        Request request = TestRequestMaker.fromString(PUTtextfileRequest.ENTIRE_MESSAGE);
        Response response = fileRequestHandler.run(request);

        assertEquals(ProtocolStrings.HTTP_METHOD_PUT, request.getMethod());
        assertEquals(StatusCodes.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    public void testGetRootDir() throws Exception {
        assertEquals(System.getProperty("user.dir") + "/www", fileRequestHandler.getRootDir());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRootDirectoryGarbageParamThrowsException() {
        fileRequestHandler = new FileRequestHandler("Something not a directory");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRootDirectoryIntegerThrowsException() {
        fileRequestHandler = new FileRequestHandler("8080");
    }

    private byte[] getTestFileBytes(String testFilePathRelativeToWWWRoot) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/www" + testFilePathRelativeToWWWRoot));
    }
}