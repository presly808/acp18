<%@include file="include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: ivpion
  Date: 27.02.18
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Menu</title>
</head>
<body>
 <%--<%ServUser transfered = (ServUser) request.getAttribute("user");%>--%>
 <c:set var="transfered" value="${user}"/>
    <div>
        <ul>
            <li><div>
                name : <c:out value="${transfered.name}"/>
            </div></li>

            <li><div>
                email : <c:out value="${transfered.email}"/>
            </div></li>

            <li><div>
                id : <c:out value="${transfered.id}"/>
            </div></li>
        </ul>
    </div>
</body>
</html>
