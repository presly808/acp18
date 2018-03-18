<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 17.03.2018
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>

<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login");
    }
%>

<div class="header">
    <h1>Menu</h1>
    <br/>
    <a href="adduser">Add User</a>
    <br/>
    <a href="allusers">Show All Users</a>
</div>

</body>
</html>
