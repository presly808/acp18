<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 14.03.2018
  Time: 0:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>

<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login");
    }
%>

<h1>Add User</h1>
<br/>

<div class="container">
    <form method="post" action="adduser">

        Name:
        <br/>
        <input name="login" type="text"/>
        <br/>
        Password:
        <br/>
        <input name="password" type="password"/>
        <br/>
        <input name="adduser" type="submit" value="Add User">

    </form>
</div>
</body>
</html>
