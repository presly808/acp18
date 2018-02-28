<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>Test Ajax</title>
</head>
<body>
    <div id="my-content">
        <label>Input Name</label>
        <input id="nameInput" type="text"><br>
        <button onclick="sendReq()">Send Ajax Req</button>
        <div id="responseText"></div>
    </div>
</body>

    <script>
        function sendReq(){
            // send
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function(){
                console.log(xhttp.readyState);
                if(xhttp.readyState === 4 && xhhtp.status === 200){
                    document.getElementById("responseText").innerHTML = xhttp.responseText;

                }
            }

            var name = document.getElementById("nameInput").value;

            xhttp.open("GET", "ajax/hello" + "?name=" + name, true);
            xhttp.send();
        }

        function sendAjexReqJquery(){

        var name = $("#nameInput").val();

            $.ajax({
                type: "GET",
                url: "ajax/hello",
                data: {name : name}, // name(ім'я об'єкта) : name (значення)
                success: function (result) {
                    $("#responseText").html(result);
                }
            })
        }

    </script>
</html>

