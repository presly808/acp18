
<%@include file="include.jsp"%>
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
<div class="container">
    <c:forEach items="${users}" var="users">
    <ul>
        <li>
            <h3>${users.name}</h3>
            ${users.email}
        </li>
    </ul>
    </c:forEach>
</div>
</body>
</html>
