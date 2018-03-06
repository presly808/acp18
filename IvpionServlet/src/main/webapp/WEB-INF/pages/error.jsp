<%--
  Created by IntelliJ IDEA.
  User: ivpion
  Date: 27.02.18
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@include file="include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1><c:out value="${errorTitle}"/></h1>
<p><c:out value="{errorMassege}"/></p>
</body>
</html>
