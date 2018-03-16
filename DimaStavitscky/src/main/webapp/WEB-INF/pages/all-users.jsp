<%@ include file="include.jsp"%>
<html>
<head>
    <title>All users</title>
</head>
<body>
    <h1>All users:</h1><br>
<ul>
<c:forEach var="user" items="${users}">
    <li>Name: ${user.name} Age: ${user.age}</li>
</c:forEach>
</ul>
</body>
</html>
