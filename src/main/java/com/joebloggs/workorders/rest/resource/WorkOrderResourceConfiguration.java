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

package com.joebloggs.workorders.rest.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkOrderResourceConfiguration {

    public static void main(final String[] args) {
        SpringApplication.run(WorkOrderResourceConfiguration.class, args);
    }

}
