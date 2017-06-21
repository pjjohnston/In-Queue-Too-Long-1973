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

package com.joebloggs.workorders.rest.dto;

/**
 * Enum class to represent the action required.
 */
public enum OperationType {
    /**
     * add order to queue.
     */
    QUEUE_ORDER,
    /**
     * get next order.
     */
    GET_NEXT_ORDER,
    /**
     * get priority list of orders.
     */
    GET_ORDER_LIST,
    /**
     * remove order from queue.
     */
    REMOVE_ORDER,
    /**
     * get order position in queue.
     */
    GET_ORDER_POSITION,
    /**
     * get average wait time for all orders.
     */
    GET_AVERAGE_WAIT_TIME,
    /**
     * no operation.
     */
    NONE;
}
