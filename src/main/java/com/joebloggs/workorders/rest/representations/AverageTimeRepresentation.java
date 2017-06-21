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
 * Representation for average time in days, hours minutes and seconds.
 */
public class AverageTimeRepresentation implements Representation {
    private String operationName;
    private String averageTimeInQueue;
    private String errorMessage = NO_ERRORS;

    public AverageTimeRepresentation() {}

    public AverageTimeRepresentation(final String operationName, final String averageTimeInQueue) {
        this.operationName = operationName;
        this.averageTimeInQueue = averageTimeInQueue;
    }

    public AverageTimeRepresentation(final String operationName, final Exception e) {
        this.operationName = operationName;
        errorMessage = e.toString();
    }

    @Override
    public String getOperationName() {
        return operationName;
    }

    public String getAverageTimeInQueue() {
        return averageTimeInQueue;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
