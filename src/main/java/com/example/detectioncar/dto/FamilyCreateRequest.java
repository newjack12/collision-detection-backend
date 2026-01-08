package com.example.detectioncar.dto;

import jakarta.validation.constraints.NotNull;

public class FamilyCreateRequest {

    @NotNull
    private Integer familyId;

    private String name;

    public FamilyCreateRequest() {}

    public Integer getFamilyId() { return familyId; }
    public void setFamilyId(Integer familyId) { this.familyId = familyId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
