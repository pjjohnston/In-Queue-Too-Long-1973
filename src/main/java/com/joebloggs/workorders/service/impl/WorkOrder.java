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

package com.joebloggs.workorders.service.impl;

import java.util.Date;

import com.joebloggs.workorders.service.util.WorkOrderUtil;

/**
 * Class for work order data.
 */
public class WorkOrder implements Comparable<WorkOrder> {
    private final RequestorIdCategory requestorIdCategory;
    private final Date requestDate;
    private final long requestorId;

    public WorkOrder(final long requestorId, final Date requestDate) {
        requestorIdCategory = RequestorIdCategory.mapIdToRequestorCategory(requestorId);
        this.requestDate = requestDate;
        this.requestorId = requestorId;
    }

    public RequestorIdCategory getRequestorIdCategory() {
        return requestorIdCategory;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public long getRequestorId() {
        return requestorId;
    }

    @Override
    public int compareTo(final WorkOrder other) {
        if (WorkOrderUtil.isManagementCategory(this) || WorkOrderUtil.isManagementCategory(other)) {
            return WorkOrderUtil.compareOrdersOneOrBothManagementCategory(this, other);
        } else {
            return WorkOrderUtil.compareOrdersByRank(this, other);
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof WorkOrder) {
            final WorkOrder otherWorkOrder = (WorkOrder) other;
            return requestorId == otherWorkOrder.getRequestorId();
        }
        return false;
    }

    @Override
    public String toString() {
        return "RequestorIdCategory: '" + requestorIdCategory + "', Request Date: '" + requestDate + "";
    }

}
