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

package com.joebloggs.workorders.rest.resource;

import static com.joebloggs.workorders.rest.util.EncodingUtil.CUSTOM_DATE_FORMAT;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joebloggs.workorders.rest.action.ActionHandler;
import com.joebloggs.workorders.rest.action.ActionHandlerFactory;
import com.joebloggs.workorders.rest.dto.OperationDataContainer;
import com.joebloggs.workorders.rest.dto.OperationType;
import com.joebloggs.workorders.rest.representations.Representation;

/**
 * REST Endpoints for Work Order Scheduling Service.
 */
@Controller
@RequestMapping("/orderqueue")
public class WorkOrderResource {

    public WorkOrderResource() {}

    /**
     * Adds an order to the queue.
     *
     * @param id
     *            id of requestor
     * @param date
     *            date of request
     * @return JSON representation of response
     */
    @RequestMapping(value = "/addorder", method = RequestMethod.POST)
    public @ResponseBody Representation addOrderToQueue(@RequestParam(value = "id") final long id,
            @RequestParam(value = "date") final @DateTimeFormat(pattern = CUSTOM_DATE_FORMAT) Date date) {
        final OperationDataContainer operationDataContainer = new OperationDataContainer(OperationType.QUEUE_ORDER, id, date);
        final ActionHandler actionHandler = ActionHandlerFactory.getActionHandler(operationDataContainer);
        return actionHandler.process(operationDataContainer);
    }

    /**
     * Retrieve the highest priority order from the queue.
     *
     * @return JSON representation of order
     */
    @RequestMapping(value = "/nextorder", method = RequestMethod.GET)
    public @ResponseBody Representation getNextOrderFromQueue() {
        final OperationDataContainer operationDataContainer = new OperationDataContainer(OperationType.GET_NEXT_ORDER);
        final ActionHandler actionHandler = ActionHandlerFactory.getActionHandler(operationDataContainer);
        return actionHandler.process(operationDataContainer);
    }

    /**
     * Retrieves a list of prioritized orders.
     *
     * @return JSON representation of prioritized list
     */
    @RequestMapping(value = "/orderlist", method = RequestMethod.GET)
    public @ResponseBody Representation getOrderPriorityList() {
        final OperationDataContainer operationDataContainer = new OperationDataContainer(OperationType.GET_ORDER_LIST);
        final ActionHandler actionHandler = ActionHandlerFactory.getActionHandler(operationDataContainer);
        return actionHandler.process(operationDataContainer);
    }

    /**
     * Removes an order from the queue.
     *
     * @param id
     *            requestor ID of order to remove
     * @return JSON representation of response
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Representation removeOrderFromQueue(@PathVariable final long id) {
        final OperationDataContainer operationDataContainer = new OperationDataContainer(OperationType.REMOVE_ORDER, id);
        final ActionHandler actionHandler = ActionHandlerFactory.getActionHandler(operationDataContainer);
        return actionHandler.process(operationDataContainer);
    }

    /**
     * Returns an orders position in the prioritised queue.
     * 
     * @param id
     *            requestor ID of order
     * @return JSON representation of position
     */
    @RequestMapping(value = "/position/{id}", method = RequestMethod.GET)
    public @ResponseBody Representation getOrderPositionInQueue(@PathVariable final long id) {
        final OperationDataContainer operationDataContainer = new OperationDataContainer(OperationType.GET_ORDER_POSITION, id);
        final ActionHandler actionHandler = ActionHandlerFactory.getActionHandler(operationDataContainer);
        return actionHandler.process(operationDataContainer);
    }

    /**
     * Returns average wait time for all orders in list.
     *
     * @param date
     *            date of client request for average
     * @return JSON representation of average wait time
     */
    @RequestMapping(value = "/waittime", method = RequestMethod.GET)
    public @ResponseBody Representation getAverageWaitTimeForOrders(@RequestParam(value = "date") final @DateTimeFormat(
            pattern = CUSTOM_DATE_FORMAT) Date date) {

        final OperationDataContainer operationDataContainer = new OperationDataContainer(OperationType.GET_AVERAGE_WAIT_TIME, date);
        final ActionHandler actionHandler = ActionHandlerFactory.getActionHandler(operationDataContainer);
        return actionHandler.process(operationDataContainer);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(CUSTOM_DATE_FORMAT);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
