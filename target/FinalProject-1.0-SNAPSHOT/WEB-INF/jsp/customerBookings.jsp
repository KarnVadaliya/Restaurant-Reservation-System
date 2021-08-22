<%-- 
    Document   : customerBookings
    Created on : Dec 7, 2020, 12:27:08 PM
    Author     : karnvadaliya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="/FinalProject/customer/mybookings/current.htm" class="button btn">Current Bookings</a>
        <br/>
        <a href="/FinalProject/customer/mybookings/past.htm" class="button btn">Past Bookings</a>
        
        <br/>
        <br/>
        <c:forEach var="item" items="${mybookings}">
            ${item.bookId} - ${item.bookingDate}
            <c:if test="${requestScope.typeofbooking == 'current'}">
                <form method="post" action="/FinalProject/customer/mybookings/cancel.htm">
                    <input value="Cancel" type="submit">
                    <input type="hidden" name="bookingid" value="${item.bookId}"/>
                </form>
            </c:if>
            <br/>
        </c:forEach>
    </body>
</html>
