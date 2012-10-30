<%-- 
    Document   : TemplateMain
    Created on : Oct 3, 2012, 11:18:45 PM
    Author     : kevingomes17
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
             ${PageTitle}
        </title>
        <link rel="stylesheet" type="text/css" href="/CS425-A2/css/style.css" />
        <script type="text/javascript" src="/CS425-A2/scripts/jquery.min.js" ></script>
        <script type="text/javascript" src="/CS425-A2/scripts/app.js" ></script>
    </head>
    <body>
        <div class="main-wrapper">
            <h1>CS425 - Assignment 2</h1>
            <div id="header">
                <ul id="primary-menu">
                    <li><a href="/CS425-A2/search/class-type.htm">Class Type</a></li>
                    <li><a href="/CS425-A2/search/class-semester.htm">Class Semester</a></li>
                    <li><a href="/CS425-A2/search/classtype-revenue.htm">Revenue</a></li>
                    <li><a href="/CS425-A2/search/person-type.htm">Person Type</a></li>
                    <li><a href="/CS425-A2/search/person-contact.htm">Contact Info</a></li>
                    <li><a href="/CS425-A2/search/revenue.htm">Revenue Impact</a></li>
                </ul>
            </div>
            <div id="content">
                <h2>${PageTitle}</h2>
                 <jsp:include page="${Filename}"></jsp:include>     
            </div>
            <div id="footer">
                Developed by: Kevin Gomes | A20271563 | kgomes@hawk.iit.edu                
            </div>
        </div>
    </body>
</html>
