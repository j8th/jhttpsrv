package com.eighthlight.jhttpsrv.shared;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

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