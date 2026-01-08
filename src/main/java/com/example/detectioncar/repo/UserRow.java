package com.example.detectioncar.repo;

/**
 * 简单的接口投影，用于 user_login + user_info 的联合查询。
 * Spring Data 会自动把 SQL 返回的字段映射到 getter 上。
 */
public interface UserRow {
    Integer getUserId();
    String getUsername();
    String getName();
    String getCarName();
    String getTel();
    String getEmail();
    Integer getState();
    Integer getFamilyId();
}
