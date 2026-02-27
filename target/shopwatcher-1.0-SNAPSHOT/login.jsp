<%--
    Document  : login
    Created on : Jan 25, 2026, 12:00:12 PM
    Author        : Anhpika
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trang Đăng Nhập</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                margin-top: 50px;
            }
            .login-form {
                border: 1px solid #ccc;
                padding: 20px;
                border-radius: 5px;
                width: 300px;
            }
            .error {
                color: red;
                margin-bottom: 10px;
            }
            input {
                display: block;
                width: 100%;
                margin-bottom: 10px;
                padding: 8px;
                box-sizing: border-box;
            }
            button {
                width: 100%;
                padding: 10px;
                background-color: #007bff;
                color: white;
                border: none;
                cursor: pointer;
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

        <link rel="stylesheet" href="css/style.css">
        <!-- Thêm icon từ FontAwesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    </head>
    <body>
        <!-- tự điền username -->
        <%
            String username_value = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("USERNAME_COOKIE")) {
                        username_value = cookie.getValue();
                    }
                }
            }
        %>
        <jsp:include page= "WEB-INF\views\header.jsp"/>
        <div class="login-form">
            <h2>Đăng Nhập</h2>

            <%-- Hiển thị thông báo lỗi nếu có từ Servlet gửi sang --%>
            <div class="error">${requestScope.error}</div>

            <form action="login" method="post">
                <label>Tên đăng nhập:</label>
                <input type="text" name="username" value="${username_value}" required>

                <label>Mật khẩu:</label>
                <input type="password" name="password" required>

                <label>Remember me</label>
                <input type="checkbox" name="remember" value="1"> <!-- tick sẽ có value = 1 -->
                <button type="submit">Đăng nhập</button>
            </form>
            <div class="footer-link">
                Don't have an account? <a href="register">Sign up now</a>
            </div>
        </div>
        <jsp:include page="WEB-INF\views\footer.jsp"/>
    </body>
</html>

