/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Joe Bloggs 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Joe Bloggs Inc. The programs may be used and/or copied only with written
 * permission from Joe Bloggs Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.joebloggs.workorders.rest.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class EncodingUtilTest {
    private static final long EPOCH_SECONDS = 0;
    private static final long ONE_DAY_IN_SECONDS = 24 * 60 * 60;
    public static final String ENCODED_DAY_ELAPSED = "1-0-0";
    public static final String ENCODED_EPOCH = "1970-01-01-01-00-00";

    @Test
    public void testEncodeDateForResponse() {
        final Date epoch = new Date(EPOCH_SECONDS);
        assertEquals(ENCODED_EPOCH, EncodingUtil.encodeDate(epoch));
    }

    @Test
    public void testConvertTimeInSecondsToDdHhMm() {
        assertEquals(ENCODED_DAY_ELAPSED, EncodingUtil.encodeTimeInSecondsToDdHhMm(ONE_DAY_IN_SECONDS));
    }

}
