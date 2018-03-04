<%--
  Created by IntelliJ IDEA.
  User: alex323glo
  Date: 01.03.18
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login here</h1>
    <hr>
    <form id="login-form" action="/login" method="post">
        <p>Email: </p>  <input id="login-email" name="email" type="text">
        <p>Password: </p>   <input id="login-password" name="password" type="password">
        <input id="login-submit" type="submit" value="login">
    </form>
    <hr>
    <a href="/index">Home</a>
</body>
</html>
