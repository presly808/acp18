<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 14.03.2018
  Time: 0:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show All Users</title>
</head>
<body>

<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login");
    }
%>



</body>
</html>
