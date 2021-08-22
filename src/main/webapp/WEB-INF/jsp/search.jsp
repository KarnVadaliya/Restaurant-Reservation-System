<%-- 
    Document   : search
    Created on : Nov 28, 2020, 12:41:39 PM
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
        <a href="/FinalProject/logout.htm" class="button btn">Log out</a>
        <form id="submitForm" method="post" action="search-restaurant.htm" >
            <input id="datefield" name="bookDate" type="date"  required value="${requestScope.bd}">
            <select id="bt" name="bookTime" >
                    <c:forEach begin="1" end="12" varStatus="loop">            
                             <option value="${loop.index}" label="${loop.index}"/>
                    </c:forEach>
            </select>
            <input type="radio" name="timeOfDay" value="AM" checked> AM
            <input type="radio" name="timeOfDay" value="PM"> PM
            <select  name="peopleNumber" id="peopl">
                    <c:forEach begin="1" end="10" varStatus="loop">
                             <option value="${loop.index}" label="${loop.index} People"/>
                    </c:forEach>
            </select>
            <select name="searchby" id="sb">
                        <option value="city" label="City"/>
                        <option value="cuisine" label="Cuisine"/>
                        <option value="name" label="Restaurant"/>
            </select>
            <input type="text" name="searchfield" value="${requestScope.sf}">
            <input value="Search" type="submit" >
        </form>
                    <a href="search-restaurant.htm" class="button btn">Clear Search</a>
                    <a href="mybookings.htm" class="button btn">View Bookings</a>
        <div>
            ${rs}- ${s}
            
            <c:if test="${restaurants == null}">
                <c:set var="bt" value="0"></c:set>
                <c:set var="peopl" value="0"></c:set>
                <c:set var="sb" value="0"></c:set>
                <c:set var="tod" value="0"></c:set>
            </c:if>
            
            
            <c:if test="${restaurants != null}">
                <c:set var="bt" value="${requestScope.bt}"></c:set>
                <c:set var="peopl" value="${requestScope.peopl}"></c:set>
                <c:set var="sb" value="${requestScope.sb}"></c:set>
                <c:set var="tod" value="${requestScope.tod}"></c:set>
            </c:if>

            <c:forEach var="item" items="${restaurants}">
                <p>${item.name}</p>
                <p>${myBookDate}</p>
                <p>${myPeople}</p>
                <p>${myBookTime}</p>
                <form method="post" action="book/confirmation.htm">
                    <input value="Book" type="submit">
                    <input type="hidden" name="restaurant" value="${item.resId}"/>
                    <input type="hidden" name="mydate" value="${myBookDate}"/>
                    <input type="hidden" name="mypeople" value="${myPeople}"/>
                    <input type="hidden" name="mytime" value="${myBookTime}"/>
                </form>
            </c:forEach>
                
                
        </div>
            <script>
            var todaysDate = function (today){
                var dd = today.getDate();
                var mm = today.getMonth()+1;
                var yyyy = today.getFullYear();
                 if(dd<10){
                        dd='0'+dd;
                    } 
                    if(mm<10){
                        mm='0'+mm;
                    } 

                today = yyyy+'-'+mm+'-'+dd;
                return today;
            };
            var today = new Date();
            var x=todaysDate(today);
            document.getElementById("datefield").setAttribute("min",x);
            Date.prototype.addDays = function(days) {
                    var date = new Date(this.valueOf());
                    date.setDate(date.getDate() + days);
                    return date;
                };
            var date = new Date();
            var z=date.addDays(30);
            var y=todaysDate(z);
            document.getElementById("datefield").setAttribute("max",y);
            
           
           
            document.getElementById("bt")[${bt}].selected="true";
            document.getElementById("peopl")[${peopl}].selected="true";
            document.getElementById("sb")[${sb}].selected="true";
            document.getElementsByName("timeOfDay")[${tod}].checked="true";

           
        </script>
    </body>
</html>
