<%--
  Created by IntelliJ IDEA.
  User: ivpion
  Date: 28.02.18
  Time: 0:40
  To change this template use File | Settings | File Templates.
--%>
<%@include file="WEB-INF/pages/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Main</title>
</head>
<body>
<div>
    <ul>
        <li><a href="register"> register</a></li>
        <li><a href="all-users">all users</a></li>
    </ul>

</div>
<c:if test="${!inSystem}">
<div>
    <form action="login" method="post">
        <ul>
            <li> Input email:
                <input name="email" type="text">
            </li>
            <li> Input password:
                <input name="password" type="password">
            </li>
            <li>
                <input type="submit" name="Click">
            </li>
        </ul>
    </form>
</div>
</c:if>
<c:if test="${inSystem}">
    <ul>
        <li>Hello<c:out value="${currentUserName}"/></li>
    </ul>
</c:if>
</body>
</html>
