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

package com.joebloggs.workorders.rest.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Utilities class for custom encoding. For decoding of dates strings @see com.joebloggs.workorders.rest.resource.WorkOrderResource#initBinder()
 */
public final class EncodingUtil {
    private static final int HOURS_TO_MINUTES = 60;
    private static final int DAYS_TO_HOURS = 24;

    private EncodingUtil() {}

    /**
     * Date encoding
     */
    public static final String CUSTOM_DATE_FORMAT = "yyyy-MM-dd-hh-mm-ss";
    /**
     * Date encoder to custom date
     */
    public static final String DATE_ENCODING_FORMATTER = "%d-%d-%d-%d-%d-%d";
    /**
     * Order age encoding in days, hours and minutes
     */
    public static final String ORDER_AGE_ENCODING_FORMATTER = "%d-%d-%d";

    /**
     * Encodes elapsed time in seconds as elapsed days, hours, minutes.
     *
     * @param averageTimeInSeconds
     *            time elapsed in seconds
     * @return elapsed time encoded as a string
     */
    public static String encodeTimeInSecondsToDdHhMm(final long averageTimeInSeconds) {
        final long days = TimeUnit.SECONDS.toDays(averageTimeInSeconds);
        final long totalHours = TimeUnit.SECONDS.toHours(averageTimeInSeconds);
        final long hours = getHours(days, totalHours);
        final long minutes = getMinutes(averageTimeInSeconds, totalHours);
        return String.format(ORDER_AGE_ENCODING_FORMATTER, days, hours, minutes);
    }

    /**
     * Encodes java.util.date to custom date format.
     *
     * @param date
     *            date object
     * @return encoded date as a string
     */
    public static String encodeDate(final Date date) {
        return new SimpleDateFormat(CUSTOM_DATE_FORMAT).format(date);
    }

    private static long getHours(final long days, final long totalHours) {
        if (days > 0) {
            return totalHours % (days * DAYS_TO_HOURS);
        }
        return totalHours;
    }

    private static long getMinutes(final long averageTimeInSeconds, final long totalHours) {
        if (totalHours > 0) {
            return TimeUnit.SECONDS.toMinutes(averageTimeInSeconds) % (totalHours * HOURS_TO_MINUTES);
        }
        return TimeUnit.SECONDS.toMinutes(averageTimeInSeconds);
    }
}
