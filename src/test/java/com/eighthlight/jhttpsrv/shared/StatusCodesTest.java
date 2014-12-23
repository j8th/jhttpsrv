package com.eighthlight.jhttpsrv.shared;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatusCodesTest {
    @Test
    public void testCodeToPhrase() {
        Assert.assertEquals(StatusCodes.OK_PHRASE, StatusCodes.CodeToPhrase(StatusCodes.OK));
        Assert.assertEquals(StatusCodes.NOT_FOUND_PHRASE, StatusCodes.CodeToPhrase(StatusCodes.NOT_FOUND));
        Assert.assertNull(StatusCodes.CodeToPhrase(0));
    }
}