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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

import com.joebloggs.workorders.service.api.WorkOrderSchedulingService;
import com.joebloggs.workorders.service.exception.EmptyQueueException;
import com.joebloggs.workorders.service.exception.ValidationException;
import com.joebloggs.workorders.service.util.WorkOrderUtil;

/**
 * Singleton wrapper Class for Priority Queue @see com.joebloggs.workorders.service.api.WorkOrderSchedulingService
 */
public class WorkOrderPriorityQueue implements WorkOrderSchedulingService {
    private static final String INVALID_ID = "Order ID invalid";
    private static final String INVALID_ID_SOLUTION = "Please submit a different ID";
    private static final String EMPTY_QUEUE = "Work order queue is empty";
    private static final String TRY_LATER = "Try again later";

    private static WorkOrderPriorityQueue INSTANCE;

    private final PriorityBlockingQueue<WorkOrder> workOrderQueue = new PriorityBlockingQueue<WorkOrder>();

    private WorkOrderPriorityQueue() {}

    public static WorkOrderPriorityQueue getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorkOrderPriorityQueue();
        }
        return INSTANCE;
    }

    @Override
    public void enqueue(final WorkOrder workOrder) throws ValidationException {
        validateId(workOrder);
        workOrderQueue.add(workOrder);
    }

    @Override
    public WorkOrder dequeue() throws EmptyQueueException {
        checkQueueEmpty();
        return workOrderQueue.poll();
    }

    @Override
    public List<Long> getIdList() throws EmptyQueueException {
        checkQueueEmpty();
        final PriorityQueue<WorkOrder> workOrderQueueCopy = new PriorityQueue<WorkOrder>(workOrderQueue);
        final List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < workOrderQueue.size(); i++) {
            idList.add(workOrderQueueCopy.poll().getRequestorId());
        }
        return idList;
    }

    @Override
    public void removeOrderFromList(final long workOrderId) throws EmptyQueueException, ValidationException {
        checkQueueEmpty();
        WorkOrder orderToRemove = null;
        for (final WorkOrder order : workOrderQueue) {
            if (order.getRequestorId() == workOrderId) {
                orderToRemove = order;
            }
        }
        if (orderToRemove == null) {
            throw new ValidationException(INVALID_ID, INVALID_ID_SOLUTION);
        } else {
            workOrderQueue.remove(orderToRemove);
        }
    }

    @Override
    public int getOrderPositionInQueue(final long workOrderId) throws EmptyQueueException, ValidationException {
        checkQueueEmpty();
        int currentPosition = 0;
        final List<Long> orderedIdList = getIdList();
        for (final Long orderId : orderedIdList) {
            if (orderId == workOrderId) {
                return currentPosition;
            }
            currentPosition++;
        }
        throw new ValidationException(INVALID_ID, INVALID_ID_SOLUTION);
    }

    @Override
    public long getAverageWaitTimeForOrders(final Date timeOfRequest) throws EmptyQueueException, ValidationException {
        checkQueueEmpty();
        long totalTimeForAllOrders = 0;
        int numOrders = 0;
        for (final WorkOrder order : workOrderQueue) {
            totalTimeForAllOrders += WorkOrderUtil.calculateNumberOfSecondsInQueue(order, timeOfRequest);
            numOrders++;
        }
        return totalTimeForAllOrders / numOrders;
    }

    public void removeAllOrdersFromQueue() {
        workOrderQueue.clear();
    }

    private void validateId(final WorkOrder workOrder) throws ValidationException {
        final long idToValidate = workOrder.getRequestorId();
        try {
            if (workOrder.getRequestorId() < 1 || getIdList().contains(idToValidate)) {
                throw new ValidationException(INVALID_ID, INVALID_ID_SOLUTION);
            }
        } catch (final EmptyQueueException e) {
            // Empty queue is valid for adding item
        }
    }

    private void checkQueueEmpty() throws EmptyQueueException {
        if (workOrderQueue.isEmpty()) {
            throw new EmptyQueueException(EMPTY_QUEUE, TRY_LATER);
        }
    }

}
