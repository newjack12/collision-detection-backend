package com.example.detectioncar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "car_name", length = 50)
    private String carName;

    @Column(name = "sensor_type", length = 50)
    private String sensorType;

    private Float ax;
    private Float ay;
    private Float az;

    @Column(name = "pressure_value")
    private Float pressureValue;

    @Column(name = "raw_data", columnDefinition = "json")
    private String rawData;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public SensorData() {}

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
