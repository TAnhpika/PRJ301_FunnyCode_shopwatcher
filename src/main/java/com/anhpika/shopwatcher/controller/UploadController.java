/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.anhpika.shopwatcher.controller;

import com.anhpika.shopwatcher.config.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.Map;

/**
 * @author Anhpika
 */
@WebServlet(name = "UploadController", urlPatterns = {"/upload"})
// annotation giúp upload file
@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // 10Mb
public class UploadController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // dùng Part vì đại diện cho nhiều loại file (ảnh, video, ...)
        Part file = request.getPart("file"); // file tương ứng vs name ở upload:  <input type="file" name="file" accept="image/*" required />

        if (file == null || file.getSize() == 0) {
            request.setAttribute("error", "File not found");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
            return;
        }

        Cloudinary cloudinary = new CloudinaryConfig(getServletContext()).getClient();

        byte[] data = file.getInputStream().readAllBytes();
        // trả về url
        Map uploadRes = cloudinary.uploader().upload(
                data,
                ObjectUtils.asMap(
                        "resource_type", "image"
                ));
        // lấy chuỗi secure_url
        String url = (String) uploadRes.get("secure_url");
        request.setAttribute("success", "DONE");
        // lấy link ảnh trong secure url để hiện lên web và lưu vào db
        request.setAttribute("imageUrl", url);
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }
}
