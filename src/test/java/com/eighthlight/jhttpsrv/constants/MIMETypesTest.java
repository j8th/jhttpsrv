package com.eighthlight.jhttpsrv.constants;

import static org.junit.Assert.*;
import org.junit.Test;

public class MIMETypesTest {
    @Test
    public void testFileExt2MIMEType() throws Exception {
        assertEquals(MIMETypes.HTML, MIMETypes.FileExt2MIMEType("html"));
        assertEquals(MIMETypes.PLAIN_TEXT, MIMETypes.FileExt2MIMEType("txt"));
        assertEquals(MIMETypes.JPEG, MIMETypes.FileExt2MIMEType("jpg"));
        assertEquals(MIMETypes.GZIP, MIMETypes.FileExt2MIMEType("gz"));
        assertEquals(MIMETypes.JSON, MIMETypes.FileExt2MIMEType("json"));
    }

    @Test
    public void testFileExt2MIMETypeWithLeadingDot() throws Exception {
        assertEquals(MIMETypes.HTML, MIMETypes.FileExt2MIMEType(".html"));
        assertEquals(MIMETypes.PLAIN_TEXT, MIMETypes.FileExt2MIMEType(".txt"));
        assertEquals(MIMETypes.JPEG, MIMETypes.FileExt2MIMEType(".jpg"));
        assertEquals(MIMETypes.GZIP, MIMETypes.FileExt2MIMEType(".gz"));
        assertEquals(MIMETypes.JSON, MIMETypes.FileExt2MIMEType(".json"));
    }

    @Test
    public void testInvalidAndUnknownFileExtensions() {
        assertEquals(MIMETypes.OCTET_STREAM, MIMETypes.FileExt2MIMEType(".some-insane-extension"));
        assertEquals(MIMETypes.OCTET_STREAM, MIMETypes.FileExt2MIMEType("."));
        assertEquals(MIMETypes.OCTET_STREAM, MIMETypes.FileExt2MIMEType(""));
    }
}