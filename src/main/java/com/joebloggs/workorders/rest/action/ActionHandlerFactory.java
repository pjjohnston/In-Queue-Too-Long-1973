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
import com.joebloggs.workorders.service.api.WorkOrderSchedulingService;
import com.joebloggs.workorders.service.impl.WorkOrderPriorityQueue;
import com.joebloggs.workorders.service.impl.WorkOrderServiceImpl;

/**
 * Factory which returns a concrete Action Handler implementation based on the operation being performed. Concrete service implementations also
 * decided
 * here.
 */
public class ActionHandlerFactory {

    public static ActionHandler getActionHandler(final OperationDataContainer operationDataContainer) {
        ActionHandler actionHandler = null;
        final WorkOrderSchedulingService schedulingService = new WorkOrderServiceImpl(WorkOrderPriorityQueue.getInstance());
        switch (operationDataContainer.getOperationType()) {
            case QUEUE_ORDER:
                actionHandler = new QueueOrderActionHandler(schedulingService);
                break;
            case GET_NEXT_ORDER:
                actionHandler = new GetNextOrderActionHandler(schedulingService);
                break;
            case GET_ORDER_LIST:
                actionHandler = new GetOrderListActionHandler(schedulingService);
                break;
            case REMOVE_ORDER:
                actionHandler = new RemoveOrderActionHandler(schedulingService);
                break;
            case GET_ORDER_POSITION:
                actionHandler = new GetOrderPositionActionHandler(schedulingService);
                break;
            case GET_AVERAGE_WAIT_TIME:
                actionHandler = new AverageTimeInQueueActionHandler(schedulingService);
                break;
            default:
        }
        return actionHandler;
    }

}
