<%--
    Document  : forget-verify
    Created on : Feb 27, 2026, 8:53:25 PM
    Author        : Anhpika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify</title>
    </head>
    <body>
        <h1>Verify</h1>
        ${requestScope.error}
        <form action="verify" method="POST">
            <input type="text" name="email" value="${sessionScope.EMAIL}" />
            <input type="text" name="otp" value="" /> <br>
            <input type="submit" value="Xác thực" />
        </form>
    </body>
</html>
