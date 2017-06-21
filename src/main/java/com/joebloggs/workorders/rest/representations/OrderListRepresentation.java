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

import java.util.ArrayList;
import java.util.List;

/**
 * Representation for list work of order ID's.
 */
public class OrderListRepresentation implements Representation {
    private String operationName;
    private List<Long> idList = new ArrayList<Long>();
    private String errorMessage = NO_ERRORS;

    public OrderListRepresentation() {}

    public OrderListRepresentation(final String operationName, final List<Long> idList) {
        this.operationName = operationName;
        this.idList = idList;
    }

    public OrderListRepresentation(final String operationName, final Exception e) {
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

    public List<Long> getIdList() {
        return idList;
    }

}
