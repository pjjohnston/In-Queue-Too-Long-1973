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

package com.joebloggs.workorders.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RequestorIdCategoryTest {
    private static final int MANAGEMENT_ID = 15;
    private static final int VIP_ID = 5;
    private static final int NORMAL_ID = 7;
    private static final int PRIORITY_ID = 3;

    @Test
    public void testMapIdToRequestorCategoryForAllIdTypes() {
        assertEquals(RequestorIdCategory.MANAGEMENT, RequestorIdCategory.mapIdToRequestorCategory(MANAGEMENT_ID));
        assertEquals(RequestorIdCategory.VIP, RequestorIdCategory.mapIdToRequestorCategory(VIP_ID));
        assertEquals(RequestorIdCategory.PRIORITY, RequestorIdCategory.mapIdToRequestorCategory(PRIORITY_ID));
        assertEquals(RequestorIdCategory.NORMAL, RequestorIdCategory.mapIdToRequestorCategory(NORMAL_ID));
    }

}
