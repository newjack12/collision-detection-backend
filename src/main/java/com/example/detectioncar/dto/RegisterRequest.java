package com.example.detectioncar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    private Integer familyId;

    // 下面这些你可以不填（不加 @NotBlank 就不会 400）
    private String tel;
    private String email;
    private String carName;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public Integer getFamilyId() { return familyId; }
    public String getTel() { return tel; }
    public String getEmail() { return email; }
    public String getCarName() { return carName; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setFamilyId(Integer familyId) { this.familyId = familyId; }
    public void setTel(String tel) { this.tel = tel; }
    public void setEmail(String email) { this.email = email; }
    public void setCarName(String carName) { this.carName = carName; }
}
