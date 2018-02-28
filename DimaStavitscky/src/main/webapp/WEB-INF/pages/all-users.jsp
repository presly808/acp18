<%@ include file="include.jsp"%>
<html>
<head>
    <title>All users</title>
</head>
<body>
<ul>
<c:forEach var="user" items="${users}">
    <li>${user}</li>
</c:forEach>
</ul>
</body>
</html>
