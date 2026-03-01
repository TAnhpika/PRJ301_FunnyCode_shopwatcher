/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhpika.shopwatcher.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Anhpika
 */
public class OtpService {

    SecureRandom random = new SecureRandom();
//    Cache<String, String> otpCache = Caffein.
    Cache<String, String> otpCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(10000)
            .build();

    public String generateAndStore(String email) {
        String otp = generateOtp();
        otpCache.put(email, otp); // lưu otp vào bộ nhớ tạm - anhpika12345@gmail.com - 123803
        return otp; // để gửi cho user
    }

    public boolean verify(String email, String otp) {
        String stored = otpCache.getIfPresent(email); // truyền vào email user xem mail có otp k -> trả về mã đang lưu trong cache
        if (stored == null) {
            return false; // sai email / chưa có forget pwd
        }
        boolean ok = stored.equals(otp); // check
        if (ok) {
            otpCache.invalidate(email); // xóa otp ở cache
        }
        return ok;
    }

    private String generateOtp() {
        StringBuilder sb = new StringBuilder(6); // tạo đoạn mã dài 6 chữ số
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); // từ 0-9
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // 1. User gửi yêu câu quên pwd
        OtpService otp = new OtpService();
        String sendOtpMail = otp.generateAndStore("anhpika12345@gmail.com"); // mã otp

        // 2. Nhận mã và gửi mail cho user quên pwd
        EmailService email = new EmailService();
        email.send("anhpika12345@gmail.com", "Mã xác thực", sendOtpMail);

        // 3. User nhập mã xác thực
        Scanner sc = new Scanner(System.in);
        String maUserNhap = sc.nextLine();

        // 4. Xác thực
        boolean oke = otp.verify("anhpika12345@gmail.com", maUserNhap);
        if (oke) {
            System.out.println("Perfect");
        } else {
            System.out.println("Failed");
        }
    }
}
