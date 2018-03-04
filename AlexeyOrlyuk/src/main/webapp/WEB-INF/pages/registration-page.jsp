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
    <title>Registration</title>
</head>
<body>
    <h1>Register here</h1>
    <hr>
    <form id="registration-form" action="/registration" method="post">
        <p>Email: </p>  <input id="registration-email" name="email" type="text">
        <p>Password: </p>   <input id="registration-password" name="password" type="password">
        <input id="registration-submit" type="submit" value="register">
    </form>
    <hr>
    <a href="/index">Home</a>
</body>
</html>
