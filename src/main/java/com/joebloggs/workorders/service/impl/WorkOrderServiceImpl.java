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

import java.util.Date;
import java.util.List;

import com.joebloggs.workorders.service.api.WorkOrderSchedulingService;
import com.joebloggs.workorders.service.exception.EmptyQueueException;
import com.joebloggs.workorders.service.exception.ValidationException;

/**
 * Concrete implementation of @see com.joebloggs.workorders.service.api.WorkOrderSchedulingService
 */
public class WorkOrderServiceImpl implements WorkOrderSchedulingService {
    private final WorkOrderSchedulingService workOrderSchedulingService = WorkOrderPriorityQueue.getInstance();

    public WorkOrderServiceImpl() {}

    @Override
    public void enqueue(final WorkOrder workOrder) throws ValidationException {
        workOrderSchedulingService.enqueue(workOrder);
    }

    @Override
    public WorkOrder dequeue() throws EmptyQueueException {
        return workOrderSchedulingService.dequeue();
    }

    @Override
    public List<Long> getIdList() throws EmptyQueueException {
        return workOrderSchedulingService.getIdList();
    }

    @Override
    public void removeOrderFromList(final long workOrderId) throws EmptyQueueException, ValidationException {
        workOrderSchedulingService.removeOrderFromList(workOrderId);
    }

    @Override
    public int getOrderPositionInQueue(final long workOrderId) throws EmptyQueueException, ValidationException {
        return workOrderSchedulingService.getOrderPositionInQueue(workOrderId);
    }

    @Override
    public long getAverageWaitTimeForOrders(final Date now) throws EmptyQueueException, ValidationException {
        return workOrderSchedulingService.getAverageWaitTimeForOrders(now);
    }

}
