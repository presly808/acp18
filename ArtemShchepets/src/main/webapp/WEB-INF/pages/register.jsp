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
		<form action="RegisterServlet" method="POST">

			Please enter your name
			<input type="text" name="un"/><br>

			Please enter your password
			<input type="password" name="pw"/>

			Please enter your age
           	<input type="text" name="age"/>

			<input type="submit" value="submit">

		</form>
	</body>
</html>