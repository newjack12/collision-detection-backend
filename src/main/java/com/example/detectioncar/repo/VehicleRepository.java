package com.example.detectioncar.repo;



import com.example.detectioncar.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByFamilyId(Integer familyId);
}
