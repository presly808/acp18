<%@ page language="java"
    contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
                                <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
		<title>Login Page</title>
	</head>

	<body>

	<%User user=session.getAttribute( "currentSessionUser");%>

		<h1> There is an error while signing in! TRY AGAIN!</h1>

		<a href="/ArtemShchepets/login">Relogin</a>

	</body>
</html>