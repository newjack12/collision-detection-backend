package com.example.detectioncar.repo;

import com.example.detectioncar.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
}
