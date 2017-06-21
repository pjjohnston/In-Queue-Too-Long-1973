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

package com.joebloggs.workorders.service.util;

import static java.lang.Math.log;
import static java.lang.Math.max;

import java.util.Date;

import com.joebloggs.workorders.service.impl.RequestorIdCategory;
import com.joebloggs.workorders.service.impl.WorkOrder;

/**
 * Utilities class for work order operations.
 */
public final class WorkOrderUtil {
    private WorkOrderUtil() {}

    private static final int CONVERT_MILLIS_TO_SECONDS = 1000;
    private static final int EQUALS = 0;
    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final String INVALID_DATE = "Invalid date";
    private static final String INVALID_DATE_SOLUTION = "Please specify another date";

    /**
     * Compares two work orders based on rank.
     *
     * @param first
     *            the first order
     * @param second
     *            the second order
     * @return integer for result of comparison
     */
    public static int compareOrdersByRank(final WorkOrder first, final WorkOrder second) {
        final double firstRank = calculateRankForWorkOrder(first);
        final double secondRank = calculateRankForWorkOrder(second);
        return firstRank > secondRank ? GREATER_THAN : firstRank < secondRank ? LESS_THAN
                : firstRank < secondRank ? EQUALS : 0;
    }

    /**
     * Compares two work orders based on ID priority type.
     *
     * @param first
     *            the first order
     * @param second
     *            the second order
     * @return integer for result of comparison
     */
    public static int compareOrdersByCategory(final WorkOrder first, final WorkOrder second) {
        final RequestorIdCategory firstCategory = first.getRequestorIdCategory();
        final RequestorIdCategory secondCategory = second.getRequestorIdCategory();
        return firstCategory.compareTo(secondCategory);
    }

    /**
     * Calculates rank for work orders.
     *
     * @param workOrder
     *            the work order.
     * @return inverted rank as a double value
     */
    public static double calculateRankForWorkOrder(final WorkOrder workOrder) {
        double rank = 0;
        switch (workOrder.getRequestorIdCategory()) {
            case NORMAL:
                rank = calculateNumberOfSecondsInQueue(workOrder);
                break;
            case PRIORITY:
                rank = calculateRankForPriorityCategory(workOrder);
                break;
            case VIP:
                rank = calculateRankForVipCategory(workOrder);
                break;
            case MANAGEMENT:
                rank = calculateNumberOfSecondsInQueue(workOrder);
                break;
            default:
        }
        return invertRank(rank);
    }

    /**
     * Inverts rank to sort from lowest to highest.
     *
     * @param rank
     *            the rank meaning highest rank first
     * @return the inverted rank
     */
    private static double invertRank(final double rank) {
        return 1.0 / rank;
    }

    /**
     * Calculates rank for the priority requestor ID type.
     *
     * @param workOrder
     *            the work order
     * @return rank as a double value
     */
    public static double calculateRankForPriorityCategory(final WorkOrder workOrder) {
        final long numberOfSecondsInQueue = calculateNumberOfSecondsInQueue(workOrder);
        return max(3, numberOfSecondsInQueue * log(numberOfSecondsInQueue));
    }

    /**
     * Calculates rank for the VIP requestor ID type.
     *
     * @param workOrder
     *            the work order
     * @return rank as a double value
     */
    public static double calculateRankForVipCategory(final WorkOrder workOrder) {
        final long numberOfSecondsInQueue = calculateNumberOfSecondsInQueue(workOrder);
        return max(4, 2 * numberOfSecondsInQueue * log(numberOfSecondsInQueue));
    }

    /**
     * Calculates the number of seconds a work order has been in the queue.
     *
     * @param workOrder
     *            the work order
     * @return seconds in queue as a long value
     */
    public static long calculateNumberOfSecondsInQueue(final WorkOrder workOrder) {
        final long nowInSeconds = convertDateToSeconds(new Date());
        final long timeAddedToQueue = convertDateToSeconds(workOrder.getRequestDate());
        final long timeWaitingInQueue = nowInSeconds - timeAddedToQueue;
        return timeWaitingInQueue > 0 ? timeWaitingInQueue : 1;
    }

    /**
     * Calculates the number of seconds a work order has been in the queue.
     *
     * @param workOrder
     *            the work order
     * @param now
     *            time client requested
     * @return seconds in queue as a long value
     */
    public static long calculateNumberOfSecondsInQueue(final WorkOrder workOrder, final Date now) {
        final long nowInSeconds = convertDateToSeconds(now);
        final long timeAddedToQueue = convertDateToSeconds(workOrder.getRequestDate());
        final long timeWaitingInQueue = nowInSeconds - timeAddedToQueue;
        if (timeWaitingInQueue < 0) {

        }
        return timeWaitingInQueue > 0 ? timeWaitingInQueue : 1;
    }

    /**
     * Checks if two work orders have the same requestor ID type.
     *
     * @param first
     *            the first order
     * @param second
     *            the second order
     * @return boolean indicating whether types match
     */
    public static boolean isSameRequestorCategory(final WorkOrder first, final WorkOrder second) {
        return compareOrdersByCategory(first, second) == 0;
    }

    /**
     * Checks if a work order has the Management requestor ID type.
     *
     * @param workOrder
     *            the work order
     * @return boolean indicating if work order has Management requestor ID type
     */
    public static boolean isManagementCategory(final WorkOrder workOrder) {
        return workOrder.getRequestorIdCategory() == RequestorIdCategory.MANAGEMENT;
    }

    /**
     * Handles comparisons where one or both work orders have the Management requestor ID type.
     *
     * @param first
     *            the first order
     * @param second
     *            the second order
     * @return
     *         integer for result of comparison
     */
    public static int compareOrdersOneOrBothManagementCategory(final WorkOrder first, final WorkOrder second) {
        if (isSameRequestorCategory(first, second)) {
            return compareOrdersByRank(first, second);
        } else {
            return compareOrdersByCategory(first, second);
        }
    }

    /**
     * Converts date object to seconds.
     *
     * @param date
     *            the date to convert
     * @return seconds as a long value
     */
    public static long convertDateToSeconds(final Date date) {
        return date.getTime() / CONVERT_MILLIS_TO_SECONDS;
    }

}
