<%-- 
    Document   : registerpage
    Created on : Apr 14, 2026, 12:29:22 AM
    Author     : herme
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link rel="stylesheet" href="loginpage.css">
    </head>
    <body>
        <div class="container">
        <h2>Register</h2>

        <form action="registerservlet" method="post"  class="login-box" >

            Name:<br>
            <input type="text" name="name" required><br>

            Email:<br>
            <input type="email" name="email" required><br>

            Password:<br>
            <input type="password" name="password" required><br>

            Confirm Password:<br>
            <input type="password" name="confirmpassword" required><br>

            <button type="submit">Register</button>
</div>
        </form>
    </body>
</html>
