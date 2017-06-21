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

import java.util.List;

import com.joebloggs.workorders.rest.dto.OperationDataContainer;
import com.joebloggs.workorders.rest.representations.OrderListRepresentation;
import com.joebloggs.workorders.rest.representations.Representation;
import com.joebloggs.workorders.service.api.WorkOrderSchedulingService;

/**
 * Action handler for retrieving highest priority order.
 * 
 * @see com.joebloggs.workorders.rest.action.ActionHandler
 */
public class GetOrderListActionHandler implements ActionHandler {
    private final WorkOrderSchedulingService schedulingService;

    public GetOrderListActionHandler(final WorkOrderSchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public Representation process(final OperationDataContainer operation) {
        final String operationType = operation.getOperationType().toString();
        try {
            final List<Long> idList = schedulingService.getIdList();
            return new OrderListRepresentation(operationType, idList);
        } catch (final Exception errorFromService) {
            return new OrderListRepresentation(operationType, errorFromService);
        }
    }

}
