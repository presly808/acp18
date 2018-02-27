<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show All</title>
</head>
<body>
<h1>Show all page</h1>
<%List list = (List) request.getAttribute("list");%>
<%int i = 0;%>
<%int max = list.size();%>

<div>
    <%while (i != max) {%>
<h6><%=list.get(i).toString()%></h6>
    <%i++;}%>
</div>
</body>
</html>