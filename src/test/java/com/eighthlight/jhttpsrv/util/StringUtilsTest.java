package com.eighthlight.jhttpsrv.util;

import com.eighthlight.jhttpsrv.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class StringUtilsTest {
    @Test
    public void testGetFileExtension() throws Exception {
        String myAbsFilePath = "/some/long/path/testfile.txt";
        String noExtension = "/some/path/to/a/file/without/an/extension";
        String trickyDotNoExt = "/Dots/In/The/Dir.Name/OhNo";
        String trickyDotWithExt = "/Dots/In/The/Dir.Name/OhNo.jpg";
        String txtGzip = "/download/testdl.txt.gz";

        Assert.assertEquals("txt", StringUtils.getFileExtension(myAbsFilePath));
        Assert.assertEquals("", StringUtils.getFileExtension(noExtension));
        Assert.assertEquals("", StringUtils.getFileExtension(trickyDotNoExt));
        Assert.assertEquals("jpg", StringUtils.getFileExtension(trickyDotWithExt));
        Assert.assertEquals("gz", StringUtils.getFileExtension(txtGzip));
    }

    @Test
    public void testParseHttpFormData() {
        String rawData = "mykey=myval&weather=cloudy&city=Chicago";

        Map<String, String> result = StringUtils.parseHttpFormData(rawData);

        Assert.assertEquals("myval", result.get("mykey"));
        Assert.assertEquals("cloudy", result.get("weather"));
        Assert.assertEquals("Chicago", result.get("city"));
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void testParseHttpFormData_OneToken() {
        String rawData = "mykey=myval";
        Map<String, String> result = StringUtils.parseHttpFormData(rawData);

        Assert.assertEquals("myval", result.get("mykey"));
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testParseHttpFormData_BadString() {
        String rawData = "no_tokens_here";
        Map<String, String> result = StringUtils.parseHttpFormData(rawData);

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testParseHttpFormData_NullString() {
        String rawData = null;
        Map<String, String> result = StringUtils.parseHttpFormData(rawData);

        Assert.assertEquals(0, result.size());
    }
}