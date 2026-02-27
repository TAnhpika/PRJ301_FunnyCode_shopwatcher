/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhpika.shopwatcher.dao;

import com.anhpika.shopwatcher.model.Account;
import jakarta.persistence.*; // nhớ tạo .*

/**
 *
 * @author Anhpika
 */
public class AccountDAO {

    // quản lý các entity - model
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("watcherManager"); // tên trong persistence.xml

    public void create(Account acc) {
        // đang muốn tạo 1 entity
        EntityManager em = emf.createEntityManager();
        // transaction - kết nối đến db -> tạo
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // tiến hành kết nối -> tạo acc
            em.persist(acc); // lấy acc đưa vào transaction để tạo record trong db
            tx.commit(); // lưu lại tiến trình
        } catch (Exception e) {
            // rollback lại phiên bản cũ khi lỗi
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close(); // đóng sau khi thực hiện kết nối -> k rò rỉ bộ nhớ / làm chậm db
        }
    }

    // Truy xuất dữ liệu: check có trong db k, k có trả về null -> k cần catch
    public Account findByUsername(String username) {
        EntityManager em = emf.createEntityManager();

        try {
            // :u ~ ?
            // Account.class: tên bảng cần truy vấn
            // getResultStream: lấy luồng dữ liệu (list)
            // findFirst: lấy đối tượng đầu tiên
            // orElse(null): k có trả về null
            // các thuộc tính bảng entity viết thường hết (username)
            return em.createQuery("SELECT a FROM Account a WHERE a.username = :u", Account.class)
                    .setParameter("u", username)
                    .getResultStream()
                    .findFirst().orElse(null);
        } finally { // truy xuất rất khó ném exception. Handle trên controller
            em.close();
        }
    }
}
