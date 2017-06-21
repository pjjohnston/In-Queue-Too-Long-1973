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

package com.joebloggs.workorders.rest.representations;

import static com.joebloggs.workorders.rest.util.RestfulServiceConstants.NO_ERRORS;

/**
 * Representation for position of order in queue.
 */
public class OrderPositionRepresentation implements Representation {
    private String operationName;
    private long id;
    private int positionInQueue;
    private String errorMessage = NO_ERRORS;

    public OrderPositionRepresentation() {}

    public OrderPositionRepresentation(final String operationName, final long orderId, final int positionInQueue) {
        this.operationName = operationName;
        id = orderId;
        this.positionInQueue = positionInQueue;
    }

    public OrderPositionRepresentation(final String operationName, final Exception e) {
        this.operationName = operationName;
        errorMessage = e.toString();
    }

    @Override
    public String getOperationName() {
        return operationName;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public long getId() {
        return id;
    }

    public int getPositionInQueue() {
        return positionInQueue;
    }

}
