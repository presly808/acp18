<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 14.03.2018
  Time: 0:50
  To change this template use File | Settings | File Templates.

--%>
<html>
<head>
    <title>Show All Users</title>
</head>
<body>

<c:if test="${session_id == null}">
   <c:redirect url="login"/>
</c:if>

<h1>Show All Users</h1>
<br/>

<table class="item-table">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Password</th>
    </tr>

    <c:forEach items="${userlist}" var="list">
    <tr>
        <td>${list.id}</td>
        <td>${list.name}</td>
        <td>${list.password}</td>
    </tr>
    </c:forEach>

</body>
</html>
