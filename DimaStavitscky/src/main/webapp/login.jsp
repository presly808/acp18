<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>Login</title>
</head>
<body>
    <div id="inputDiv">
        <input id="inputLogin">Login<br>
        <input id="inptPass" type="password">Password<br>
        <button onclick="sendLoginAndPass()">Sign in</button>
    </div>
</body>

    <script>
        function sendLoginAndPass(){
            var login = $("#inputLogin").val();
            var pass = $("#inptPass").val();

            $.ajax({
                type: "POST",
                url: "/login",
                data: { login : login,
                        pass : pass }

            })
        }



    </script>
</html>
