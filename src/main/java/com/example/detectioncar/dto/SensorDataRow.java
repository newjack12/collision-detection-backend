package com.example.detectioncar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SensorDataRow {

    private Integer id;
    private Integer userId;
    private String carName;
    private String sensorType;
    private Float ax;
    private Float ay;
    private Float az;
    private Float pressureValue;
    private String rawData;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public SensorDataRow() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getCarName() { return carName; }
    public void setCarName(String carName) { this.carName = carName; }

    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }

    public Float getAx() { return ax; }
    public void setAx(Float ax) { this.ax = ax; }

    public Float getAy() { return ay; }
    public void setAy(Float ay) { this.ay = ay; }

    public Float getAz() { return az; }
    public void setAz(Float az) { this.az = az; }

    public Float getPressureValue() { return pressureValue; }
    public void setPressureValue(Float pressureValue) { this.pressureValue = pressureValue; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
