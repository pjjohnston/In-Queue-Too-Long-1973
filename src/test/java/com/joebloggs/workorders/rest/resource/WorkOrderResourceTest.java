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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.joebloggs.workorders.rest.representations.AverageTimeRepresentation;
import com.joebloggs.workorders.rest.representations.OperationResultRepresentation;
import com.joebloggs.workorders.rest.representations.OrderListRepresentation;
import com.joebloggs.workorders.rest.representations.OrderPositionRepresentation;
import com.joebloggs.workorders.rest.representations.WorkOrderRepresentation;
import com.joebloggs.workorders.service.exception.EmptyQueueException;
import com.joebloggs.workorders.service.exception.ValidationException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class WorkOrderResourceTest {
    // URI's
    private static final String RESOURCE_URI = "http://localhost:8080/orderqueue";
    private static final String ADD_ORDER_URI = RESOURCE_URI + "/addorder";
    public static final String ADD_ORDER_URI_FORMATTER = "%s?id=%d&date=%s";
    private static final String NEXT_ORDER_URI = RESOURCE_URI + "/nextorder";
    public static final String ORDER_LIST_URI = RESOURCE_URI + "/orderlist";
    public static final String REMOVE_ORDER_URI = RESOURCE_URI + "/remove";
    public static final String SUBMIT_ID_URI_FORMATTER = "%s/%d";
    public static final String ORDER_POSITION_URI = RESOURCE_URI + "/position";
    public static final String WAIT_TIME_URI = RESOURCE_URI + "/waittime";
    public static final String WAIT_TIME_URI_FORMATTER = "%s?date=%s";

    public static final String ADD_DATE = "2017-06-19-10-01-11";
    public static final String ONE_DAY_LATER = "2017-06-20-10-01-11";
    public static final String ONE_HOUR_LATER = "2017-06-19-11-01-11";
    public static final String ONE_MINUTE_LATER = "2017-06-19-10-02-11";
    public static final String ONE_DAY = "1-0-0";
    public static final String ONE_HOUR = "0-1-0";
    public static final String ONE_MINUTE = "0-0-1";

    private static final long MANAGEMENT_ID = 15;
    private static final long NORMAL_ID = 7;
    private static final long VIP_ID = 5;
    private static final long INVALID_ID = 0;
    private static final long NON_EXISTENT_ID = 123;
    private static final String NO_ERRORS = "NO ERRORS";

    @Autowired
    private WorkOrderResourceConfiguration controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @After
    public void teardown() {
        removeAllOrdersFromQueue();
    }

    @Test
    public void testEnqueueForValidQueue() throws ValidationException, EmptyQueueException {
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(VIP_ID, ADD_DATE);

        assertEquals(0, getPositionInQueue(VIP_ID));
        assertEquals(1, getPositionInQueue(NORMAL_ID));
    }

    @Test
    public void testEnqueueForInvalidId() throws ValidationException {
        final OperationResultRepresentation response = addOrderRequest(INVALID_ID, ADD_DATE);
        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testEnqueueForDuplicateId() throws ValidationException {
        addOrderRequest(VIP_ID, ADD_DATE);
        final OperationResultRepresentation response = addOrderRequest(VIP_ID, ADD_DATE);
        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testDequeueForValidQueue() throws ValidationException, EmptyQueueException {
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(VIP_ID, ADD_DATE);

        assertEquals(VIP_ID, getNextOrderId());
        assertEquals(NORMAL_ID, getNextOrderId());
    }

    @Test
    public void testDequeueForEmptyQueue() throws EmptyQueueException {
        final WorkOrderRepresentation response = getNextOrderRequest();
        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testGetIdListForValidQueue() throws ValidationException, EmptyQueueException {
        addOrderRequest(VIP_ID, ADD_DATE);
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(MANAGEMENT_ID, ADD_DATE);

        final List<Long> expected = Arrays.asList(MANAGEMENT_ID, VIP_ID, NORMAL_ID);
        assertThat(getPriorityList(), is(expected));
    }

    @Test
    public void testGetIdListForEmptyQueue() throws ValidationException, EmptyQueueException {
        final OrderListRepresentation response = getPriorityListRequest();
        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testRemoveOrderForValidQueue() throws ValidationException, EmptyQueueException {
        addOrderRequest(VIP_ID, ADD_DATE);
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(MANAGEMENT_ID, ADD_DATE);

        assertTrue(getPriorityList().contains(MANAGEMENT_ID));

        removeOrderRequest(MANAGEMENT_ID);

        assertFalse(getPriorityList().contains(MANAGEMENT_ID));
    }

    @Test
    public void testRemoveOrderForNonExistentId() throws ValidationException, EmptyQueueException {
        addOrderRequest(VIP_ID, ADD_DATE);
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(MANAGEMENT_ID, ADD_DATE);

        final OperationResultRepresentation response = removeOrderRequest(NON_EXISTENT_ID);

        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testRemoveOrderForEmptyQueue() throws ValidationException, EmptyQueueException {
        final OperationResultRepresentation response = removeOrderRequest(MANAGEMENT_ID);
        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testGetOrderPositionInQueueForValidQueue() throws ValidationException, EmptyQueueException {
        addOrderRequest(VIP_ID, ADD_DATE);
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(MANAGEMENT_ID, ADD_DATE);

        assertEquals(0, getPositionInQueue(MANAGEMENT_ID));
        assertEquals(1, getPositionInQueue(VIP_ID));
        assertEquals(2, getPositionInQueue(NORMAL_ID));
    }

    @Test
    public void testGetOrderPositionInQueueForEmptyQueue() throws ValidationException, EmptyQueueException {
        final OrderPositionRepresentation response = getPositionInQueueRequest(NORMAL_ID);
        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testGetOrderPositionInQueueForNonExistingId() throws ValidationException, EmptyQueueException {
        addOrderRequest(VIP_ID, ADD_DATE);
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(MANAGEMENT_ID, ADD_DATE);

        final OrderPositionRepresentation response = getPositionInQueueRequest(NON_EXISTENT_ID);

        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    @Test
    public void testGetAverageWaitTimeForOrdersForValidQueue() throws ValidationException, EmptyQueueException {
        addOrderRequest(VIP_ID, ADD_DATE);
        addOrderRequest(NORMAL_ID, ADD_DATE);
        addOrderRequest(MANAGEMENT_ID, ADD_DATE);

        assertEquals(ONE_DAY, getAverageWaitTime(ONE_DAY_LATER));
        assertEquals(ONE_HOUR, getAverageWaitTime(ONE_HOUR_LATER));
        assertEquals(ONE_MINUTE, getAverageWaitTime(ONE_MINUTE_LATER));
    }

    @Test
    public void testGetAverageWaitTimeForOrdersForEmptyQueue() throws ValidationException, EmptyQueueException {
        final AverageTimeRepresentation response = getAverageWaitTimeRequest(ONE_HOUR_LATER);
        assertFalse(response.getErrorMessage().equals(NO_ERRORS));
    }

    private OperationResultRepresentation addOrderRequest(final long id, final String date) {
        final String addHigherOrderUri = String.format(ADD_ORDER_URI_FORMATTER, ADD_ORDER_URI, id, date);

        final ResponseEntity<OperationResultRepresentation> addOrderToQueueResponse = restTemplate.exchange(addHigherOrderUri,
                HttpMethod.POST, null, new ParameterizedTypeReference<OperationResultRepresentation>() {});

        return addOrderToQueueResponse.getBody();
    }

    private WorkOrderRepresentation getNextOrderRequest() {
        final ResponseEntity<WorkOrderRepresentation> response = restTemplate.exchange(NEXT_ORDER_URI,
                HttpMethod.GET, null, new ParameterizedTypeReference<WorkOrderRepresentation>() {});
        return response.getBody();
    }

    private long getNextOrderId() {
        return getNextOrderRequest().getId();
    }

    private OrderListRepresentation getPriorityListRequest() {
        final ResponseEntity<OrderListRepresentation> response = restTemplate.exchange(ORDER_LIST_URI,
                HttpMethod.GET, null, new ParameterizedTypeReference<OrderListRepresentation>() {});
        return response.getBody();
    }

    private List<Long> getPriorityList() {
        return getPriorityListRequest().getIdList();
    }

    private OperationResultRepresentation removeOrderRequest(final long id) {
        final String removeOrderUri = String.format(SUBMIT_ID_URI_FORMATTER, REMOVE_ORDER_URI, id);
        final ResponseEntity<OperationResultRepresentation> response = restTemplate.exchange(removeOrderUri,
                HttpMethod.DELETE, null, new ParameterizedTypeReference<OperationResultRepresentation>() {});
        return response.getBody();
    }

    private OrderPositionRepresentation getPositionInQueueRequest(final long id) {
        final String uri = String.format(SUBMIT_ID_URI_FORMATTER, ORDER_POSITION_URI, id);
        final ResponseEntity<OrderPositionRepresentation> response = restTemplate.exchange(uri,
                HttpMethod.GET, null, new ParameterizedTypeReference<OrderPositionRepresentation>() {});
        return response.getBody();
    }

    private int getPositionInQueue(final long id) {
        return getPositionInQueueRequest(id).getPositionInQueue();
    }

    private AverageTimeRepresentation getAverageWaitTimeRequest(final String date) {
        final String uri = String.format(WAIT_TIME_URI_FORMATTER, WAIT_TIME_URI, date);
        final ResponseEntity<AverageTimeRepresentation> response = restTemplate.exchange(uri,
                HttpMethod.GET, null, new ParameterizedTypeReference<AverageTimeRepresentation>() {});
        return response.getBody();
    }

    private String getAverageWaitTime(final String date) {
        return getAverageWaitTimeRequest(date).getAverageTimeInQueue();
    }

    private void removeAllOrdersFromQueue() {
        final List<Long> idList = getPriorityList();
        for (final long id : idList) {
            removeOrderRequest(id);
        }
    }
}
