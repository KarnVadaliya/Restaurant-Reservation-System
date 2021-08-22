<%-- 
    Document   : userRegister
    Created on : Nov 27, 2020, 5:33:02 PM
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
        <form:form method="post" action="customer-register.htm" modelAttribute="customerDetails">
            <form:label path="uemail">Email: </form:label>
            <form:input path="uemail" /><form:errors path="uemail" /><br/><br />
            
            <form:label path="upassword">Password: </form:label>
            <form:password path="upassword"/><form:errors path="upassword" /><br/><br />
            
            <form:label path="cpassword">Confirm Password: </form:label>
            <form:password path="cpassword"/><form:errors path="cpassword" /><br/><br />
            
            <form:label path="name">Name: </form:label>
            <form:input path="name"/><form:errors path="name" /><br/><br />
            
            <form:label path="phone">Phone: </form:label>
            <form:input path="phone"/><form:errors path="phone" /><br/><br />
            <c:out value="${requestScope.error}"/>
            <br/>
            <input type="submit" value="Register" />
        </form:form>
    </body>
</html>
