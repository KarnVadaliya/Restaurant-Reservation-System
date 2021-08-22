<%-- 
    Document   : bookingInfo
    Created on : Dec 11, 2020, 5:35:58 PM
    Author     : karnvadaliya
--%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="/FinalProject/admin/booking-information.htm">
            <input type="text" name="bid" value="" />
            <input value="Enter Booking ID" type="submit">
        </form>
    <c:out value="${errmsg}"></c:out>
    <c:out value="${resultmsg}"></c:out>
    <c:if test="${b != null}">
        ${b.bookId}
        ${b.getCustomers().getName()}
    </c:if>
    
    </body>
</html>
