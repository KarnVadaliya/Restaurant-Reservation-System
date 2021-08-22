<%-- 
    Document   : pendingRestaurants
    Created on : Dec 11, 2020, 5:35:42 PM
    Author     : karnvadaliya
--%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:out value="${errmsg}"></c:out>
           
        <c:forEach var="item" items="${restaurants}">
                <p>${item.resId}  <=>  ${item.name}</p>
                <form method="get" action="/FinalProject/admin/pending-requests/restaurantDetails.htm">
                    <input value="View Details" type="submit">
                    <input type="hidden" name="restaurant" value="${item.resId}"/>
                </form>
            </c:forEach>
    </body>
</html>
