<%-- 
    Document   : login
    Created on : Nov 27, 2020, 12:55:44 PM
    Author     : karnvadaliya
--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form:form method="post" action="login.htm" modelAttribute="loginDetails">
            <form:label path="email">Email: </form:label>
            <form:input path="email" /><form:errors path="email" /><br/>
            <form:label path="pass">Password: </form:label>
            <form:input path="pass"/><form:errors path="pass" /><br/><br />
            <input type="submit" value="Login" />
        </form:form>
         <c:out value="${requestScope.msg}"/>
        <a href="register.htm" class="button btn">Register</a>
    </body>
</html>
