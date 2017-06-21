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

import java.util.Date;

/**
 * Container for data relating to service operations.
 */
public class OperationDataContainer {
    private OperationType operationType = OperationType.NONE;
    private long workOrderId = 0;;
    private Date requestDate = null;

    public OperationDataContainer() {}

    public OperationDataContainer(final OperationType operationType) {
        this.operationType = operationType;
    }

    public OperationDataContainer(final OperationType operationType, final long workOrderId) {
        this.operationType = operationType;
        this.workOrderId = workOrderId;
    }

    public OperationDataContainer(final OperationType operationType, final Date requestDate) {
        this.operationType = operationType;
        this.requestDate = requestDate;
    }

    public OperationDataContainer(final OperationType operationType, final long workOrderId, final Date requestDate) {
        this.operationType = operationType;
        this.workOrderId = workOrderId;
        this.requestDate = requestDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public long getWorkOrderId() {
        return workOrderId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

}
