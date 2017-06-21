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

package com.joebloggs.workorders.service.api;

import java.util.Date;
import java.util.List;

import com.joebloggs.workorders.service.exception.EmptyQueueException;
import com.joebloggs.workorders.service.exception.ValidationException;
import com.joebloggs.workorders.service.impl.WorkOrder;

/**
 * Public API for Work Order Scheduling Service. Extend this interface to add future services.
 */
public interface WorkOrderSchedulingService {

    /**
     * Places the work order on the priority queue.
     *
     * @param workOrder
     *            the work order
     * @throws ValidationException
     */
    public void enqueue(final WorkOrder workOrder) throws ValidationException;

    /**
     * Retrieves the highest priority work order.
     *
     * @return the highest priority work order
     * @throws EmptyQueueException
     */
    public WorkOrder dequeue() throws EmptyQueueException;

    /**
     * Retrieves the prioritized list of orders.
     *
     * @return prioritized list of orders
     * @throws EmptyQueueException
     */
    public List<Long> getIdList() throws EmptyQueueException;

    /**
     * Removes an order from the queue.
     *
     * @param workOrderId
     *            the ID of the order to be removed
     * @throws EmptyQueueException
     * @throws ValidationException
     */
    public void removeOrderFromList(final long workOrderId) throws EmptyQueueException, ValidationException;

    /**
     * Returns the position of an order in the queue.
     *
     * @param workOrderId
     *            the ID of the order
     * @return the position in the queue
     * @throws EmptyQueueException
     * @throws ValidationException
     */
    public int getOrderPositionInQueue(final long workOrderId) throws EmptyQueueException, ValidationException;

    /**
     * Returns the average wait time for all orders.
     *
     * @param currentTime
     *            the current time
     * @return the average wait time
     * @throws EmptyQueueException
     * @throws ValidationException
     */
    public long getAverageWaitTimeForOrders(Date currentTime) throws EmptyQueueException, ValidationException;

}
