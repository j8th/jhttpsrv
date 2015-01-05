package com.eighthlight.jhttpsrv.constants;

import org.junit.Assert;
import org.junit.Test;

public class StatusCodesTest {
    @Test
    public void testCodeToPhrase() {
        Assert.assertEquals(StatusCodes.OK_PHRASE, StatusCodes.CodeToPhrase(StatusCodes.OK));
        Assert.assertEquals(StatusCodes.NOT_FOUND_PHRASE, StatusCodes.CodeToPhrase(StatusCodes.NOT_FOUND));
        Assert.assertNull(StatusCodes.CodeToPhrase(0));
    }
}