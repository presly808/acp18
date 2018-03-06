
<%@include file="include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: ivpion
  Date: 28.02.18
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>All users</title>
</head>
<h1>Users list</h1>
<body>
<div>
    <c:forEach items="${users}" var="users">
    <ul>
        <li>
            <h3><c:out value="${users.name}"/></h3>
            <c:out value="${users.value}"/>
        </li>
    </ul>
    </c:forEach>
</div>
</body>
</html>
