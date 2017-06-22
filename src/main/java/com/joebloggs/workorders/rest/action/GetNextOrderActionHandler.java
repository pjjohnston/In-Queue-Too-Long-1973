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
import com.joebloggs.workorders.rest.representations.Representation;
import com.joebloggs.workorders.rest.representations.WorkOrderRepresentation;
import com.joebloggs.workorders.service.api.WorkOrderSchedulingService;
import com.joebloggs.workorders.service.impl.WorkOrder;

/**
 * Action handler for getting next order from queue.
 *
 * @see com.joebloggs.workorders.rest.action.ActionHandler
 */
public class GetNextOrderActionHandler implements ActionHandler {
    private final WorkOrderSchedulingService schedulingService;

    public GetNextOrderActionHandler(final WorkOrderSchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public Representation process(final OperationDataContainer operation) {
        final String operationType = operation.getOperationType().toString();
        try {
            final WorkOrder nextOrder = schedulingService.dequeue();
            return new WorkOrderRepresentation(operationType, nextOrder);
        } catch (final Exception errorFromService) {
            return new WorkOrderRepresentation(operationType, errorFromService);
        }
    }

}
