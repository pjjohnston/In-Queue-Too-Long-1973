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

import com.joebloggs.workorders.rest.dto.OperationDataContainer;
import com.joebloggs.workorders.rest.representations.OrderPositionRepresentation;
import com.joebloggs.workorders.rest.representations.Representation;
import com.joebloggs.workorders.service.api.WorkOrderSchedulingService;

/**
 * Action handler for retrieving position of order in queue.
 * 
 * @see com.joebloggs.workorders.rest.action.ActionHandler
 */
public class GetOrderPositionActionHandler implements ActionHandler {
    private final WorkOrderSchedulingService schedulingService;

    public GetOrderPositionActionHandler(final WorkOrderSchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public Representation process(final OperationDataContainer operation) {
        final String operationType = operation.getOperationType().toString();
        try {
            final long workOrderId = operation.getWorkOrderId();
            final int positionInQueue = schedulingService.getOrderPositionInQueue(workOrderId);
            return new OrderPositionRepresentation(operationType, workOrderId, positionInQueue);
        } catch (final Exception errorFromService) {
            return new OrderPositionRepresentation(operationType, errorFromService);
        }
    }

}
