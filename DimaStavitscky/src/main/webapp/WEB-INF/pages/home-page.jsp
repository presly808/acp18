<%@ include file="include.jsp" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <div>
        <h1>Home Page</h1><br>
        <form action="addUser">
            <ul>
                <li>Add user
                    <input type="submit">
                </li>
            </ul>
        </form>
        <%--<a href="all-users.jsp">All users</a>--%>

        <form action="allUsers">
            <ul>
                <li>All users
                    <input type="submit">
                </li>
            </ul>
        </form>
    </div>

</body>
</html>
