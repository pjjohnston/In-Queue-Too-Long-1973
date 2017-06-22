**************************************************
Joe Bloggs Inc - Order Prioiritisation Web Service
**************************************************

=== Note
For javadocs see joe-bloggs-queue-order/doc/index.html

********
Sections
********

== (1) Installation

== (2) Starting the server

== (3) REST Interface Description (includes encoding)


****************
(1) Installation
****************

=== Note
These steps only need to be done once.

=== Install JDK 1.7 or later
http://docs.oracle.com/javase/7/docs/webnotes/install/

=== Install Maven 3 or later
https://maven.apache.org/download.cgi

=== CD into root directory of project (joe-bloggs-queue-order - current directory)
There you should see the following files:
mvnw  mvnw.cmd  pom.xml

=== Confirm project build and run unit tests
mvn clean install

***********************
(2) Starting the server
***********************

=== Start the web server
./mvnw spring-boot:run

You will see text similar to the following on the console:

2017-06-19 22:22:51.025  INFO 11345 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-06-19 22:22:51.035  INFO 11345 --- [           main] c.j.w.r.r.WorkOrderResourceConfiguration : Started WorkOrderResourceConfiguration in 5.017 seconds (JVM running for 10.699)

Confirm the service is up by pasting the following URI into the browser:

http://localhost:8080/health

You should see the following text in the browser window

{"status":"UP"}

This indicates that the service is listening on port 8080
The server is now ready to take requests


**************************************************
(3) REST Interface Description (includes encoding)
**************************************************

=== Note
The server will default to http://localhost:8080/orderqueue/
You can configure a different port in the file:
joe-bloggs-queue-order/src/main/resources/application.properties
See
http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-change-the-http-port

The server accepts the following client requests

1) Add an order to the queue

2) Retrieve the highest prioirity order

3) Retrieve a full list of all order in priority order

4) Remove an order from the queue

5) Retrieve the position of an order in the queue

6) Retrieve the average wait time for all orders in the queue


**********************
(1) Add order to queue
**********************
=== Note
The server will respond with an error if
The order ID is invalid (already in use or less than 1)

=== Note 
Dates are encoded in the following format:
"yyyy-MM-dd-hh-mm-ss"

=== URI format
http://<server>:<port>/orderqueue/addorder?id=<ID of order>&date=<Date>

=== URI example
Submit order with id 15 on 19th of June, 2017 at 10:01 and 11 seconds
http://localhost:8080/orderqueue/addorder?id=15&date=2017-06-19-10-01-11

**********************************
(2) Get the highest priority order
**********************************
=== Note
The server will respond with an error if the queue is empty

=== URI format
http://<server>:<port>/orderqueue/nextorder

=== URI example
http://localhost:8080/orderqueue/nextorder

**********************************
(3) Get the prioritized order list
**********************************
=== Note
The server will respond with an error if the queue is empty

=== URI format
http://<server>:<port>/orderqueue/orderlist

=== URI example
http://localhost:8080/orderqueue/orderlist

**********************************
(4) Remove an order from the queue
**********************************
=== Note
The server will respond with an error if:
1) the queue is empty
2) the order ID does not exist

=== URI format
http://<server>:<port>/orderqueue/remove/{id}

=== URI example
Remove order with id 15 from the queue
http://localhost:8080/orderqueue/remove/15

***************************************
(5) Get an orders position in the queue
***************************************
=== Note
The server will respond with an error if:
1) the queue is empty
2) the order ID does not exist

=== URI format
http://<server>:<port>/orderqueue/position/{id}

=== URI example
Get position of order with id 15
http://localhost:8080/orderqueue/position/15

********************************************
(6) Get the average wait time for all orders
********************************************
=== Note
The server will respond with an error if the queue is empty

=== Note
Dates are encoded in the following format:
"yyyy-MM-dd-hh-mm-ss"
Wait times are encoded in the following format:
"dd-hh-mm"

=== URI format
http://<server>:<port>/orderqueue/waittime?date=<date>

=== URI example
Check the average wait time for orders on on 16th of July, 2017 at 09:11 and 54 seconds
http://localhost:8080/orderqueue/waittime?date=2017-07-16-09-11-54










