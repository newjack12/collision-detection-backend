package com.example.detectioncar.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleCreateRequest {
    @NotNull
    private Integer familyId;

    @NotBlank
    private String name;

    private String model;
    private String sensorId;

    public Integer getFamilyId() { return familyId; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getSensorId() { return sensorId; }

    public void setFamilyId(Integer familyId) { this.familyId = familyId; }
    public void setName(String name) { this.name = name; }
    public void setModel(String model) { this.model = model; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
}
