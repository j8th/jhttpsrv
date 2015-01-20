package com.eighthlight.jhttpsrv.constants;

import org.junit.Assert;
import org.junit.Test;

public class MIMETypesTest {
    @Test
    public void testFileExt2MIMEType() throws Exception {
        Assert.assertEquals(MIMETypes.HTML, MIMETypes.FileExt2MIMEType("html"));
        Assert.assertEquals(MIMETypes.PLAIN_TEXT, MIMETypes.FileExt2MIMEType("txt"));
        Assert.assertEquals(MIMETypes.JPEG, MIMETypes.FileExt2MIMEType("jpg"));
        Assert.assertEquals(MIMETypes.GZIP, MIMETypes.FileExt2MIMEType("gz"));
        Assert.assertEquals(MIMETypes.JSON, MIMETypes.FileExt2MIMEType("json"));
    }

    @Test
    public void testFileExt2MIMETypeWithLeadingDot() throws Exception {
        Assert.assertEquals(MIMETypes.HTML, MIMETypes.FileExt2MIMEType(".html"));
        Assert.assertEquals(MIMETypes.PLAIN_TEXT, MIMETypes.FileExt2MIMEType(".txt"));
        Assert.assertEquals(MIMETypes.JPEG, MIMETypes.FileExt2MIMEType(".jpg"));
        Assert.assertEquals(MIMETypes.GZIP, MIMETypes.FileExt2MIMEType(".gz"));
        Assert.assertEquals(MIMETypes.JSON, MIMETypes.FileExt2MIMEType(".json"));
    }

    @Test
    public void testInvalidAndUnknownFileExtensions() {
        Assert.assertEquals(MIMETypes.OCTET_STREAM, MIMETypes.FileExt2MIMEType(".some-insane-extension"));
        Assert.assertEquals(MIMETypes.OCTET_STREAM, MIMETypes.FileExt2MIMEType("."));
        Assert.assertEquals(MIMETypes.OCTET_STREAM, MIMETypes.FileExt2MIMEType(""));
    }
}