/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.anhpika.shopwatcher.controller;

import com.anhpika.shopwatcher.dao.AccountDAO;
import com.anhpika.shopwatcher.model.Account;
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
@WebServlet(name = "AuthController", urlPatterns = {"/register", "/login"})
public class AuthController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login")) {
            getLogin(request, response);
        } else if (uri.contains("register")) {
            getRegister(request, response);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login")) {
            postLogin(request, response);
        } else if (uri.contains("register")) {
            postRegister(request, response);
        }
    }
    private final AccountDAO dao = new AccountDAO();

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
}
