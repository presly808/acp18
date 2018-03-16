<%@ include file="include.jsp" %>
<html>
<head>
    <title>Add users</title>
</head>
<body>
    <c:out value="result" default="Add user: "/>

    <form method="post" action="addUser">
        <ul>
            <li>Input name:
                <input name="name">
            </li>
            <li>Input age:
                <input name="age" type="number">
            </li>
            <li>Input salary:
                <input name="salary" type="number">
            </li>
            <li>Input login:
                <input name="login">
            </li>
            <li>Input pass:
                <input name="pass">
            </li>
            <li>Input Department:
                <input name="department">
            </li>
            <li>Input City:
                <input name="city">
            </li>
            <li>Input Manager id:
                <input name="managerId" type="number">
            </li>
            <li>Submit:
                <input type="submit">
            </li>
        </ul>
    </form>

</body>
</html>
