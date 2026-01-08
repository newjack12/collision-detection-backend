package com.example.detectioncar.dto;

import java.util.Map;

/**
 * ESP32 / IoT
 *
 * sensor_data：
 * - userId -> user_id
 * - carName -> car_name
 * - sensorType -> sensor_type
 * - ax/ay/az -> ax/ay/az
 * - pressureValue -> pressure_value
 * - rawData -> raw_data
 */
public class IotSensorIngestRequest {

    private Integer userId;
    private String carName;
    private String sensorType;

    private Float pressureValue;
    private Float ax;
    private Float ay;
    private Float az;

    /** 允许把原始包整个塞进来，后端会转成 JSON 字符串存 raw_data */
    private Map<String, Object> rawData;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getCarName() { return carName; }
    public void setCarName(String carName) { this.carName = carName; }

    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }

    public Float getPressureValue() { return pressureValue; }
    public void setPressureValue(Float pressureValue) { this.pressureValue = pressureValue; }

    public Float getAx() { return ax; }
    public void setAx(Float ax) { this.ax = ax; }

    public Float getAy() { return ay; }
    public void setAy(Float ay) { this.ay = ay; }

    public Float getAz() { return az; }
    public void setAz(Float az) { this.az = az; }

    public Map<String, Object> getRawData() { return rawData; }
    public void setRawData(Map<String, Object> rawData) { this.rawData = rawData; }
}
