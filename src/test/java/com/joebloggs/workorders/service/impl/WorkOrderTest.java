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

import java.util.Date;

import org.junit.Test;

public class WorkOrderTest {
    private static final int EQUALS = 0;
    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final int MANAGEMENT_ID = 15;
    private static final int NORMAL_ID = 7;
    private static final int VIP_ID = 5;
    private static final int PRIORITY_ID = 3;

    @Test
    public void testCreateNormalOrder() {
        final Date dateAddedToQueue = new Date();
        final WorkOrder objUnderTest = new WorkOrder(NORMAL_ID, dateAddedToQueue);

        assertEquals(dateAddedToQueue, objUnderTest.getRequestDate());
        assertEquals(RequestorIdCategory.NORMAL, objUnderTest.getRequestorIdCategory());
    }

    @Test
    public void testCreatePriorityOrder() {
        final Date dateAddedToQueue = new Date();
        final WorkOrder objUnderTest = new WorkOrder(PRIORITY_ID, dateAddedToQueue);

        assertEquals(dateAddedToQueue, objUnderTest.getRequestDate());
        assertEquals(RequestorIdCategory.PRIORITY, objUnderTest.getRequestorIdCategory());
    }

    @Test
    public void testCreateVipOrder() {
        final Date dateAddedToQueue = new Date();
        final WorkOrder objUnderTest = new WorkOrder(VIP_ID, dateAddedToQueue);

        assertEquals(dateAddedToQueue, objUnderTest.getRequestDate());
        assertEquals(RequestorIdCategory.VIP, objUnderTest.getRequestorIdCategory());
    }

    @Test
    public void testCreateManagementOrder() {
        final Date dateAddedToQueue = new Date();
        final WorkOrder objUnderTest = new WorkOrder(MANAGEMENT_ID, dateAddedToQueue);

        assertEquals(dateAddedToQueue, objUnderTest.getRequestDate());
        assertEquals(RequestorIdCategory.MANAGEMENT, objUnderTest.getRequestorIdCategory());
    }

    @Test
    public void testCompareOrdersBothManagementCategory() {
        final WorkOrder firstManagementOrder = new WorkOrder(MANAGEMENT_ID, new Date());

        sleepToAllowPriorityChange();

        final WorkOrder secondManagementOrder = new WorkOrder(MANAGEMENT_ID, new Date());

        assertEquals(LESS_THAN, firstManagementOrder.compareTo(secondManagementOrder));
    }

    @Test
    public void testCompareOrdersOneIsManagementCategory() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder managementOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        final WorkOrder vipOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);

        assertEquals(LESS_THAN, managementOrder.compareTo(vipOrder));
    }

    @Test
    public void testCompareOrdersNeitherIsManagementCategory() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder normalOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder priorityOrder = new WorkOrder(PRIORITY_ID, addedToQueueInSameSecond);
        final WorkOrder vipOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);

        assertEquals(LESS_THAN, vipOrder.compareTo(priorityOrder));
        assertEquals(LESS_THAN, priorityOrder.compareTo(normalOrder));
    }

    private void sleepToAllowPriorityChange() {
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
