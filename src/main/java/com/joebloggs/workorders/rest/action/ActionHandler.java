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
import com.joebloggs.workorders.rest.representations.Representation;

/**
 * Interface for actions against service.
 */
public interface ActionHandler {
    /**
     * Performs operations against order scheduling service.
     *
     * @param operation
     *            the operation to be performed
     * @return the REST representation for operation result
     */
    public Representation process(OperationDataContainer operation);
}
