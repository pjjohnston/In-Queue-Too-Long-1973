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

package com.joebloggs.workorders.service.util;

import static java.lang.Math.log;
import static java.lang.Math.max;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.joebloggs.workorders.service.impl.WorkOrder;

public class WorkOrderUtilTest {
    private static final int EQUALS = 0;
    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final int MANAGEMENT_ID = 15;
    private static final int NORMAL_ID = 7;
    private static final int VIP_ID = 5;
    private static final int PRIORITY_ID = 3;

    @Test
    public void testCompareOrdersByRankAllPermutations() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);

        assertEquals(LESS_THAN, WorkOrderUtil.compareOrdersByRank(higherPriorityOrder, lowerPriorityOrder));
        assertEquals(GREATER_THAN, WorkOrderUtil.compareOrdersByRank(lowerPriorityOrder, higherPriorityOrder));
        assertEquals(EQUALS, WorkOrderUtil.compareOrdersByRank(lowerPriorityOrder, lowerPriorityOrder));
    }

    @Test
    public void testCompareOrdersByCategoryAllPermutations() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);

        assertTrue(WorkOrderUtil.compareOrdersByCategory(higherPriorityOrder, lowerPriorityOrder) < 0);
        assertTrue(WorkOrderUtil.compareOrdersByCategory(lowerPriorityOrder, higherPriorityOrder) > 0);
        assertTrue(WorkOrderUtil.compareOrdersByCategory(lowerPriorityOrder, lowerPriorityOrder) == 0);
    }

    @Test
    public void testCalculateRankForWorkOrderAllPermutations() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder normalPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder vipPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder middlePriorityOrder = new WorkOrder(PRIORITY_ID, addedToQueueInSameSecond);

        assertEquals(1, WorkOrderUtil.calculateRankForWorkOrder(normalPriorityOrder), 0);
        assertEquals(1 / max(4, 2 * log(1)), WorkOrderUtil.calculateRankForWorkOrder(vipPriorityOrder), 0);
        assertEquals(1 / max(3, 1 * log(1)), WorkOrderUtil.calculateRankForWorkOrder(middlePriorityOrder), 0);
    }

    @Test
    public void testCalculateRankForPriorityCategory() {
        final WorkOrder priorityOrder = new WorkOrder(PRIORITY_ID, new Date());

        assertEquals(max(3, 1 * log(1)), WorkOrderUtil.calculateRankForPriorityCategory(priorityOrder), 0);
    }

    @Test
    public void testCalculateRankForVipCategory() {
        final WorkOrder vipOrder = new WorkOrder(VIP_ID, new Date());

        assertEquals(max(4, 2 * log(1)), WorkOrderUtil.calculateRankForVipCategory(vipOrder), 0);
    }

    @Test
    public void testCalculateNumberOfSecondsInQueue() {
        final WorkOrder normalOrder = new WorkOrder(NORMAL_ID, new Date());

        assertEquals(1, WorkOrderUtil.calculateNumberOfSecondsInQueue(normalOrder), 0);
    }

    @Test
    public void testCalculateNumberOfSecondsInQueueFromNow() {
        final Date clientRequestTime = new Date();
        final WorkOrder normalOrder = new WorkOrder(NORMAL_ID, new Date());

        assertEquals(1, WorkOrderUtil.calculateNumberOfSecondsInQueue(normalOrder, clientRequestTime), 0);
    }

    @Test
    public void testIsSameRequestorCategoryBothSame() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder firstManagementOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        final WorkOrder secondManagementOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);

        assertTrue(WorkOrderUtil.isSameRequestorCategory(firstManagementOrder, secondManagementOrder));
    }

    @Test
    public void testIsSameRequestorCategoryBothNotSame() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder managementOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        final WorkOrder prioritytOrder = new WorkOrder(PRIORITY_ID, addedToQueueInSameSecond);

        assertFalse(WorkOrderUtil.isSameRequestorCategory(managementOrder, prioritytOrder));
    }

    @Test
    public void testIsManagementCategory() {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder managementOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        final WorkOrder prioritytOrder = new WorkOrder(PRIORITY_ID, addedToQueueInSameSecond);

        assertTrue(WorkOrderUtil.isManagementCategory(managementOrder));
        assertFalse(WorkOrderUtil.isManagementCategory(prioritytOrder));
    }

    @Test
    public void testCompareOrdersOneOrBothManagementCategory() {
        final WorkOrder firstManagementOrder = new WorkOrder(MANAGEMENT_ID, new Date());

        sleepToAllowPriorityChange();

        final WorkOrder secondManagementOrder = new WorkOrder(MANAGEMENT_ID, new Date());
        final WorkOrder secondPrioritytOrder = new WorkOrder(PRIORITY_ID, new Date());

        assertEquals(LESS_THAN, WorkOrderUtil.compareOrdersOneOrBothManagementCategory(firstManagementOrder, secondManagementOrder));
        assertTrue(WorkOrderUtil.compareOrdersOneOrBothManagementCategory(firstManagementOrder, secondPrioritytOrder) < 0);
    }

    private void sleepToAllowPriorityChange() {
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
