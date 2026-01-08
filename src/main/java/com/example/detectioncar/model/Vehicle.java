package com.example.detectioncar.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="family_id", nullable=false)
    private Integer familyId;

    @Column(nullable=false)
    private String name;

    private String model;

    @Column(name="sensor_id")
    private String sensorId;

    private String status;

    @Column(name="last_update")
    private LocalDateTime lastUpdate;

    public Integer getId() { return id; }
    public Integer getFamilyId() { return familyId; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getSensorId() { return sensorId; }
    public String getStatus() { return status; }
    public LocalDateTime getLastUpdate() { return lastUpdate; }

    public void setId(Integer id) { this.id = id; }
    public void setFamilyId(Integer familyId) { this.familyId = familyId; }
    public void setName(String name) { this.name = name; }
    public void setModel(String model) { this.model = model; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public void setStatus(String status) { this.status = status; }
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }
}
