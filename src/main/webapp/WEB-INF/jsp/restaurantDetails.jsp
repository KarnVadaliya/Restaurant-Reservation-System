<%-- 
    Document   : restaurantDetails
    Created on : Dec 11, 2020, 5:44:16 PM
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
        <c:if test="${r != null}">
                Restaurant Name : ${r.name} <br/>
                <form method="post" action="/FinalProject/admin/pending-requests/restaurantDetails/decline.htm">
                    <input type="text" name="reason" value="" required/>
                    <input value="Decline" type="submit">
                    <input type="hidden" name="rid" value="${r.resId}"/>
                </form>
                <form method="post" action="/FinalProject/admin/pending-requests/restaurantDetails/accept.htm">
                    <input value="Accept" type="submit">
                    <input type="hidden" name="rid" value="${r.resId}"/>
                </form>
        </c:if>
    </body>
</html>
