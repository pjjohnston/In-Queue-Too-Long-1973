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
import static com.joebloggs.workorders.rest.util.RestfulServiceConstants.OPERATION_FAILED;
import static com.joebloggs.workorders.rest.util.RestfulServiceConstants.OPERATION_SUCCESS;

/**
 * Representation for operation success.
 */
public class OperationResultRepresentation implements Representation {
    private String operationName;
    private long id;
    private String status;
    private String errorMessage = NO_ERRORS;

    public OperationResultRepresentation() {}

    public OperationResultRepresentation(final String operationName, final long workOrderId) {
        this.operationName = operationName;
        id = workOrderId;
        status = OPERATION_SUCCESS;
    }

    public OperationResultRepresentation(final String operationName, final Exception e) {
        this.operationName = operationName;
        errorMessage = e.toString();
        status = OPERATION_FAILED;
    }

    @Override
    public String getOperationName() {
        return operationName;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
