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

package com.joebloggs.workorders.rest.action;

import java.util.Date;

import com.joebloggs.workorders.rest.dto.OperationDataContainer;
import com.joebloggs.workorders.rest.representations.OperationResultRepresentation;
import com.joebloggs.workorders.rest.representations.Representation;
import com.joebloggs.workorders.service.api.WorkOrderSchedulingService;
import com.joebloggs.workorders.service.exception.ValidationException;
import com.joebloggs.workorders.service.impl.WorkOrder;

/**
 * Action handler for queuing orders.
 * 
 * @see com.joebloggs.workorders.rest.action.ActionHandler
 */
public class QueueOrderActionHandler implements ActionHandler {
    private static final String INVALID_ID = "Invalid order ID";
    private static final String SOLUTION = "Please use an ID greater than zero";

    private final WorkOrderSchedulingService schedulingService;

    public QueueOrderActionHandler(final WorkOrderSchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public Representation process(final OperationDataContainer operation) {
        final String operationType = operation.getOperationType().toString();
        final long workOrderId = operation.getWorkOrderId();
        if (!isValidId(workOrderId)) {
            return new OperationResultRepresentation(operationType, new ValidationException(INVALID_ID, SOLUTION));
        }
        try {
            final Date requestDate = operation.getRequestDate();
            final WorkOrder orderToQueue = new WorkOrder(workOrderId, requestDate);
            schedulingService.enqueue(orderToQueue);
            return new OperationResultRepresentation(operationType, workOrderId);
        } catch (final Exception errorFromService) {
            return new OperationResultRepresentation(operationType, errorFromService);
        }
    }

    private boolean isValidId(final long workOrderId) {
        return workOrderId > 0;
    }

}
