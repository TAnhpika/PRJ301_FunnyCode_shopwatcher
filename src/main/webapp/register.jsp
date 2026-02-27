<%--
    Document  : register
    Created on : Jan 25, 2026, 3:14:17 PM
    Author        : Anhpika
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create Account</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                display: flex;
                justify-content: center;
                padding-top: 50px;
                background-color: #f0f2f5;
            }
            .container {
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                width: 350px;
            }
            h2 {
                text-align: center;
                color: #1c1e21;
                margin-bottom: 20px;
            }
            input {
                width: 100%;
                padding: 12px;
                margin: 8px 0;
                border: 1px solid #ddd;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 14px;
            }
            button {
                width: 100%;
                padding: 12px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
                font-weight: bold;
                margin-top: 10px;
            }
            button:hover {
                background-color: #0056b3;
            }
            .error {
                color: red;
                text-align: center;
                margin-bottom: 10px;
            }
            .success {
                color: #1e7e34;
                background: #d4edda;
                padding: 10px;
                border-radius: 4px;
                font-size: 13px;
                text-align: center;
                margin-bottom: 15px;
                border: 1px solid #c3e6cb;
            }
            .footer-link {
                text-align: center;
                margin-top: 20px;
                font-size: 14px;
                color: #606770;
            }
            .footer-link a {
                color: #007bff;
                text-decoration: none;
            }
            .footer-link a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h2>Create account</h2>

            <!-- k cần dùng c:if vì scope k có sẽ null - k hiện -->
            <div class="error">${requestScope.error}</div>

            <form action="register" method="post">
                <input type="text" name="username" placeholder="Username" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="password" placeholder="Password" required>

                <button type="submit">Sign up</button>
            </form>

            <div class="footer-link">
                Already have an account? <a href="login.jsp">Login here</a>
            </div>
        </div>
    </body>
</html>
