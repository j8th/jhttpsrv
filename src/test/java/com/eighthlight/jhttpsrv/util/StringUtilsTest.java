package com.eighthlight.jhttpsrv.util;

import com.eighthlight.jhttpsrv.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {
    @Test
    public void testGetFileExtension() throws Exception {
        String myAbsFilePath = "/some/long/path/testfile.txt";
        String noExtension = "/some/path/to/a/file/without/an/extension";
        String trickyDotNoExt = "/Dots/In/The/Dir.Name/OhNo";
        String trickyDotWithExt = "/Dots/In/The/Dir.Name/OhNo.jpg";

        Assert.assertEquals("txt", StringUtils.getFileExtension(myAbsFilePath));
        Assert.assertEquals("", StringUtils.getFileExtension(noExtension));
        Assert.assertEquals("", StringUtils.getFileExtension(trickyDotNoExt));
        Assert.assertEquals("jpg", StringUtils.getFileExtension(trickyDotWithExt));
    }
}