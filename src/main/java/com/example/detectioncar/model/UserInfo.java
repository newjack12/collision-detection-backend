package com.example.detectioncar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(length = 50)
    private String name;

    @Column(name = "car_name", length = 50)
    private String carName;

    @Column(length = 20)
    private String tel;

    @Column(length = 100)
    private String email;

    // 0 disabled, 1 enabled (INT in DB)
    @Column(nullable = false)
    private Integer state;

    @Column(name = "family_id")
    private Integer familyId;

    public UserInfo() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCarName() { return carName; }
    public void setCarName(String carName) { this.carName = carName; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getState() { return state; }
    public void setState(Integer state) { this.state = state; }

    public Integer getFamilyId() { return familyId; }
    public void setFamilyId(Integer familyId) { this.familyId = familyId; }
}
