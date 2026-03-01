/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.anhpika.shopwatcher.controller;

import com.anhpika.shopwatcher.dao.AccountDAO;
import com.anhpika.shopwatcher.model.Account;
import com.anhpika.shopwatcher.utils.EmailService;
import com.anhpika.shopwatcher.utils.OtpService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Anhpika
 */
@WebServlet(name = "AuthController", urlPatterns = { "/register", "/login", "/forget", "/verify" })
public class AuthController extends HttpServlet {

    private final AccountDAO dao = new AccountDAO();
    private final OtpService otp = new OtpService();
    private final EmailService emailService = new EmailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login")) {
            getLogin(request, response);
        } else if (uri.contains("register")) {
            getRegister(request, response);
        } else if (uri.contains("forget")) {
            getForgetpass(request, response);
        } else if (uri.contains("verify")) {
            getVerify(request, response);
        }
    }

    private void getLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void getRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private void getForgetpass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forget-email.jsp").forward(request, response);
    }

    private void getVerify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forget-verify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login")) {
            postLogin(request, response);
        } else if (uri.contains("register")) {
            postRegister(request, response);
        } else if (uri.contains("forget")) {
            postForget(request, response);
        } else if (uri.contains("verify")) {
            postVerify(request, response);
        }
    }

    private void postLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        // Giải mã BCrypt
        Account acc = dao.findByUsername(username);
        // acc.getPassword() : pwd đã mã hóa -> k dùng equal đc
        if (acc != null && BCrypt.checkpw(password, acc.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("account", acc);

            // remember me:
            if (remember != null) {
                Cookie c = new Cookie("USERNAME_COOKIE", username);
                c.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(c);
            } else {
                // xóa cookie
                Cookie c = new Cookie("USERNAME_COOKIE", "");
                c.setMaxAge(0);
                response.addCookie(c);
            }
            response.sendRedirect("index.html");

        } else {
            request.setAttribute("error", "Username or password is incorrect. Try again");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void postRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check username đã tồn tại trc đó hay chưa
        if (dao.findByUsername(username) != null) {
            request.setAttribute("error", "Username existed!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            Account acc = new Account();
            acc.setUsername(username);
            acc.setEmail(email);
            // hashpw: mã hóa pwd (password, salt)
            String pwdHash = BCrypt.hashpw(password, BCrypt.gensalt());
            acc.setPassword(pwdHash);
            dao.create(acc);
            response.sendRedirect("login.jsp"); // k gửi bất kỳ data nào vì create đã lưu thẳng vào db r
        }
    }

    private void postForget(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String stored = otp.generateAndStore("anhpika12345@gmail.com"); // mã otp
        emailService.send(email, "Mã xác thực", stored);
        HttpSession session = request.getSession();
        session.setAttribute("EMAIL", email);
        response.sendRedirect("verify");
    }

    private void postVerify(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String email = request.getParameter("email");
        String otpUser = request.getParameter("otp");
        boolean oke = otp.verify(email, otpUser);
        if (oke) {
            response.sendRedirect("index.html");
        } else {
            request.setAttribute("error", "THAT BAI");
            request.getRequestDispatcher("forget-verify.jsp").forward(request, response);
        }
    }
}
