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

import com.joebloggs.workorders.rest.util.EncodingUtil;
import com.joebloggs.workorders.service.impl.WorkOrder;

/**
 * Representation for Work Order.
 */
public class WorkOrderRepresentation implements Representation {
    private String operationName;
    private long id;
    private String category;
    private String date;
    private String errorMessage = NO_ERRORS;

    public WorkOrderRepresentation() {}

    public WorkOrderRepresentation(final String operationName, final WorkOrder workOrder) {
        this.operationName = operationName;
        id = workOrder.getRequestorId();
        category = workOrder.getRequestorIdCategory().toString();
        date = EncodingUtil.encodeDate(workOrder.getRequestDate());
    }

    public WorkOrderRepresentation(final String operationName, final Exception e) {
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

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public void setOperationName(final String operationName) {
        this.operationName = operationName;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public void setDate(final String date) {
        this.date = date;
    }

}
