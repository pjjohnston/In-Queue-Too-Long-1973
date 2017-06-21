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

/**
 * Enum class to represent the requestor category.
 */
public enum RequestorIdCategory {
    /**
     * management category.
     */
    MANAGEMENT,
    /**
     * VIP category.
     */
    VIP,
    /**
     * priority category.
     */
    PRIORITY,
    /**
     * normal category.
     */
    NORMAL;

    private static final int DIVISOR_1 = 3;
    private static final int DIVISOR_2 = 5;

    /**
     * Static method which maps the requestor ID to category
     *
     * @param idValue
     *            long value representing the requestor's ID
     * @return enum repesenting requestor's category
     */
    public static RequestorIdCategory mapIdToRequestorCategory(final long idValue) {
        if (isRequestorIdDivisibleBy(idValue, DIVISOR_1) && isRequestorIdDivisibleBy(idValue, DIVISOR_2)) {
            return RequestorIdCategory.MANAGEMENT;
        } else if (isRequestorIdDivisibleBy(idValue, DIVISOR_2)) {
            return RequestorIdCategory.VIP;
        } else if (isRequestorIdDivisibleBy(idValue, DIVISOR_1)) {
            return RequestorIdCategory.PRIORITY;
        }
        return RequestorIdCategory.NORMAL;
    }

    private static boolean isRequestorIdDivisibleBy(final long idValue, final int divisor) {
        return idValue % divisor == 0;
    }

}
