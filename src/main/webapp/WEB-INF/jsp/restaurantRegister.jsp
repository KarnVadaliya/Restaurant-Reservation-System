<%-- 
    Document   : restaurantRegister
    Created on : Nov 28, 2020, 12:44:53 AM
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
        <form:form method="post" action="restaurant-register.htm" modelAttribute="restaurantDetails">
            <form:label path="name">Restaurant Name: </form:label>
            <form:input path="name" /><form:errors path="name" /><br/><br />
            
            <form:label path="uemail">Email: </form:label>
            <form:input path="uemail" /><form:errors path="uemail" /><br/><br />
            
            <form:label path="upassword">Password: </form:label>
            <form:input path="upassword"/><form:errors path="upassword" /><br/><br />
            
            <form:label path="cpassword">Confirm Password: </form:label>
            <form:input path="cpassword"/><form:errors path="cpassword" /><br/><br />
            
            <form:label path="number">Phone: </form:label>
            <form:input path="number"/><form:errors path="number" /><br/><br />
            
            <form:label path="totaltable">Total Tables: </form:label>
            <form:input path="totaltable"/><form:errors path="totaltable" /><br/><br />
            
            <form:label path="openingtime">Opening Time: </form:label>
            <form:select path="openingtime" multiple="false">
                    <c:forEach begin="0" end="23" varStatus="loop">
                        <form:option value="${loop.index}" label="${loop.index}"/>
                    </c:forEach>
            </form:select>
            <form:errors path="openingtime" /><br/><br />
            
            <form:label path="closingtime">Closing Time: </form:label>
            <form:select path="closingtime" multiple="false">
                    <c:forEach begin="0" end="23" varStatus="loop">
                        <form:option value="${loop.index}" label="${loop.index}"/>
                    </c:forEach>
            </form:select>
            <form:errors path="closingtime" /><br/><br />
            
            
            <label>Select Cuisines: </label><br/>
            <select name="rcuisines" multiple="true">
                <c:forEach var="item" items="${cuisineList}">
                <option value="${item.cuisineName}" label="${item.cuisineName}"/>
                </c:forEach>
            </select>
            <br/><br />
            
            <form:label path="street">Street: </form:label>
            <form:textarea path="street" rows="2" cols="20"/><form:errors path="street" /><br/><br />
            
            
            <form:label path="city">City: </form:label>
            <form:input path="city"/><form:errors path="city" /><br/><br />
            
            <form:label path="zipcode">Zip Code: </form:label>
            <form:input path="zipcode"/><form:errors path="zipcode" /><br/><br />
            
            <c:out value="${requestScope.error}"/>
            <br/>
            <input type="submit" value="Register" />
        </form:form>
    </body>
</html>
