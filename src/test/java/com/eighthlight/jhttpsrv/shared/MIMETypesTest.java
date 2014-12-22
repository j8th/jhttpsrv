package com.eighthlight.jhttpsrv.shared;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MIMETypesTest {
    @Test
    public void testFileExt2MIMEType() throws Exception {
        Assert.assertEquals(MIMETypes.HTML, MIMETypes.FileExt2MIMEType("html"));
        Assert.assertEquals(MIMETypes.PLAIN_TEXT, MIMETypes.FileExt2MIMEType("txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFileExtension() {
        MIMETypes.FileExt2MIMEType(".some-insane-extension");
    }
}