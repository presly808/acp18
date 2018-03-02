<%@include file="include.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: ivpion
  Date: 27.02.18
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <div class="container">
        <h1>Input register info</h1>

        <form method="post" action="register">
            <ul>
                <li> Input name:
                    <input name="name" type="text">
                </li>
                <li> Input email:
                    <input name="email" type="text">
                </li>
                <li> Input password:
                    <input name="password" type="password">
                </li>
                <li>
                    <input type="submit">
                </li>
            </ul>
        </form>
    </div>

</body>
</html>

<%-- <%ServUser transfered = (ServUser) request.getAttribute("user");%>
    <div class="container">
        <ul>
            <li><div class="column">
                name : <%= transfered.getName()%>
            </div></li>

            <li><div class="column">
                email : <%= transfered.getEmail()%>
            </div></li>

            <li><div class="column">
                id : <%= transfered.getId()%>
            </div></li>
        </ul>
    </div>--%>
