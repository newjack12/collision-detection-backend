package com.example.detectioncar.controller;

import com.example.detectioncar.dto.IotSensorIngestRequest;
import com.example.detectioncar.model.SensorData;
import com.example.detectioncar.repo.SensorDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/iot")
public class IotIngestController {

    private final SensorDataRepository repo;
    private final ObjectMapper om = new ObjectMapper();


    @Value("${iot.key:}")
    private String iotKey;

    public IotIngestController(SensorDataRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/sensor-data")
    public Map<String, Object> ingest(
            @RequestBody IotSensorIngestRequest req,
            @RequestHeader(value = "X-IOT-KEY", required = false) String key
    ) throws Exception {


        if (req.getUserId() == null) {
            throw new RuntimeException("userId required");
        }

        SensorData row = new SensorData();
        row.setUserId(req.getUserId());
        row.setCarName(req.getCarName());
        row.setSensorType(req.getSensorType());
        row.setPressureValue(req.getPressureValue());
        row.setAx(req.getAx());
        row.setAy(req.getAy());
        row.setAz(req.getAz());

        if (req.getRawData() != null) {
            row.setRawData(om.writeValueAsString(req.getRawData()));
        } else {
            row.setRawData(null);
        }

        repo.save(row);

        Map<String, Object> resp = new HashMap<>();
        resp.put("ok", true);
        return resp;
    }
}
