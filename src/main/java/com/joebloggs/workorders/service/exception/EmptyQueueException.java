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

package com.joebloggs.workorders.service.exception;

/**
 * Empty Queue Exception implementation.
 */
public class EmptyQueueException extends Exception {

    private static final long serialVersionUID = 5496664793544977316L;
    private final String solution;

    /**
     * Constructor which takes the message of the exception.
     *
     * @param message
     *            of the exception
     * @param solution
     *            suggested solution
     */
    public EmptyQueueException(final String message, final String solution) {
        super(message);
        this.solution = solution;
    }

    /**
     * Gets the suggested solution.
     *
     * @return the solution
     */
    public String solution() {
        return solution;
    }

    @Override
    public String toString() {
        return "Error: '" + super.getMessage() + "', Solution: '" + solution + "";
    }

}
