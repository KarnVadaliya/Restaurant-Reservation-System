<%-- 
    Document   : adminRegister
    Created on : Dec 11, 2020, 4:44:03 PM
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
        <form:form method="post" action="admin-register.htm" modelAttribute="adminDetails">
            <form:label path="uemail">Email: </form:label>
            <form:input path="uemail" /><form:errors path="uemail" /><br/><br />
            
            <form:label path="upassword">Password: </form:label>
            <form:password path="upassword"/><form:errors path="upassword" /><br/><br />
            
            <form:label path="cpassword">Confirm Password: </form:label>
            <form:password path="cpassword"/><form:errors path="cpassword" /><br/><br />
            
            <c:out value="${requestScope.error}"/>
            <br/>
            <input type="submit" value="Register" />
        </form:form>
    </body>
</html>
