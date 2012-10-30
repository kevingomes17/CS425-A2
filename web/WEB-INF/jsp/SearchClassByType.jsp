<%-- 
    Document   : SearchClass
    Created on : Oct 3, 2012, 10:26:36 PM
    Author     : kevingomes17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="search-classes" method="POST">
    Enter class type: <input type="text" name="classType" value="${classType}"/>    
    <input type="submit" value="Search"/>
    
    <c:if test="${classes.size() > 0}">
        <ol>
            <c:forEach items="${classes}" var="class">
                <li>${class.title}</li>
            </c:forEach>
        </ol>
    </c:if>
    
    <c:if test="${noResults == 1}">
        No results found!
    </c:if>
        
    ${revenueMessage}
</form> 
