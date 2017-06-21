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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.joebloggs.workorders.service.exception.EmptyQueueException;
import com.joebloggs.workorders.service.exception.ValidationException;

public class WorkOrderPriorityQueueTest {
    private static final long MANAGEMENT_ID = 15;
    private static final long NORMAL_ID = 7;
    private static final long VIP_ID = 5;
    private static final long INVALID_ID = 0;
    private static final long NON_EXISTENT_ID = 123;

    private WorkOrderPriorityQueue objUnderTest;

    @Before
    public void setup() {
        objUnderTest = WorkOrderPriorityQueue.getInstance();
    }

    @After
    public void teardown() {
        objUnderTest.removeAllOrdersFromQueue();
    }

    @Test
    public void testEnqueueForValidQueue() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);

        assertEquals(0, objUnderTest.getOrderPositionInQueue(higherPriorityOrder.getRequestorId()));
        assertEquals(1, objUnderTest.getOrderPositionInQueue(lowerPriorityOrder.getRequestorId()));
    }

    @Test(expected = ValidationException.class)
    public void testEnqueueForInvalidId() throws ValidationException {
        final WorkOrder workOrder = new WorkOrder(INVALID_ID, new Date());
        objUnderTest.enqueue(workOrder);
    }

    @Test(expected = ValidationException.class)
    public void testEnqueueForDuplicateId() throws ValidationException {
        final WorkOrder workOrder = new WorkOrder(VIP_ID, new Date());
        final WorkOrder duplicateWorkOrder = new WorkOrder(VIP_ID, new Date());
        objUnderTest.enqueue(workOrder);
        objUnderTest.enqueue(duplicateWorkOrder);
    }

    @Test
    public void testDequeueForValidQueue() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);

        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);

        assertEquals(higherPriorityOrder, objUnderTest.dequeue());
        assertEquals(lowerPriorityOrder, objUnderTest.dequeue());
    }

    @Test(expected = EmptyQueueException.class)
    public void testDequeueForEmptyQueue() throws EmptyQueueException {
        objUnderTest.dequeue();
    }

    @Test
    public void testGetIdListForValidQueue() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder highestPriorityOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);
        objUnderTest.enqueue(highestPriorityOrder);

        final List<Long> expected = Arrays.asList(MANAGEMENT_ID, VIP_ID, NORMAL_ID);

        assertThat(objUnderTest.getIdList(), is(expected));
    }

    @Test(expected = EmptyQueueException.class)
    public void testGetIdListForEmptyQueue() throws ValidationException, EmptyQueueException {
        objUnderTest.getIdList();
    }

    @Test
    public void testRemoveOrderForValidQueue() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder highestPriorityOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);
        objUnderTest.enqueue(highestPriorityOrder);

        assertTrue(objUnderTest.getIdList().contains(higherPriorityOrder.getRequestorId()));

        objUnderTest.removeOrderFromList(higherPriorityOrder.getRequestorId());

        assertFalse(objUnderTest.getIdList().contains(higherPriorityOrder.getRequestorId()));
    }

    @Test(expected = ValidationException.class)
    public void testRemoveOrderForNonExistentId() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder highestPriorityOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);
        objUnderTest.enqueue(highestPriorityOrder);

        objUnderTest.removeOrderFromList(NON_EXISTENT_ID);
    }

    @Test(expected = EmptyQueueException.class)
    public void testRemoveOrderForEmptyQueue() throws ValidationException, EmptyQueueException {
        objUnderTest.removeOrderFromList(MANAGEMENT_ID);
    }

    @Test
    public void testGetOrderPositionInQueueForValidQueue() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder highestPriorityOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);
        objUnderTest.enqueue(highestPriorityOrder);

        assertEquals(0, objUnderTest.getOrderPositionInQueue(highestPriorityOrder.getRequestorId()));
        assertEquals(1, objUnderTest.getOrderPositionInQueue(higherPriorityOrder.getRequestorId()));
        assertEquals(2, objUnderTest.getOrderPositionInQueue(lowerPriorityOrder.getRequestorId()));
    }

    @Test(expected = EmptyQueueException.class)
    public void testGetOrderPositionInQueueForEmptyQueue() throws ValidationException, EmptyQueueException {
        objUnderTest.getOrderPositionInQueue(MANAGEMENT_ID);
    }

    @Test(expected = ValidationException.class)
    public void testGetOrderPositionInQueueForNonExistingId() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder highestPriorityOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);
        objUnderTest.enqueue(highestPriorityOrder);

        objUnderTest.getOrderPositionInQueue(NON_EXISTENT_ID);
    }

    @Test
    public void testGetAverageWaitTimeForOrdersForValidQueue() throws ValidationException, EmptyQueueException {
        final Date addedToQueueInSameSecond = new Date();
        final WorkOrder higherPriorityOrder = new WorkOrder(VIP_ID, addedToQueueInSameSecond);
        final WorkOrder lowerPriorityOrder = new WorkOrder(NORMAL_ID, addedToQueueInSameSecond);
        final WorkOrder highestPriorityOrder = new WorkOrder(MANAGEMENT_ID, addedToQueueInSameSecond);
        objUnderTest.enqueue(lowerPriorityOrder);
        objUnderTest.enqueue(higherPriorityOrder);
        objUnderTest.enqueue(highestPriorityOrder);

        assertEquals(1L, objUnderTest.getAverageWaitTimeForOrders(new Date()));
    }

    @Test(expected = EmptyQueueException.class)
    public void testGetAverageWaitTimeForOrdersForEmptyQueue() throws ValidationException, EmptyQueueException {
        objUnderTest.getAverageWaitTimeForOrders(new Date());
    }

}
