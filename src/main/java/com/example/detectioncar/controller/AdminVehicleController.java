package com.example.detectioncar.controller;


import com.example.detectioncar.dto.VehicleCreateRequest;
import com.example.detectioncar.model.Vehicle;
import com.example.detectioncar.repo.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminVehicleController {

    private final VehicleRepository vehicleRepository;

    public AdminVehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> list(@RequestParam Integer familyId) {
        return vehicleRepository.findByFamilyId(familyId);
    }

    @PostMapping("/vehicles")
    public Vehicle create(@Valid @RequestBody VehicleCreateRequest req) {
        Vehicle v = new Vehicle();
        v.setFamilyId(req.getFamilyId());
        v.setName(req.getName());
        v.setModel(req.getModel());
        v.setSensorId(req.getSensorId());
        v.setStatus("offline");
        v.setLastUpdate(LocalDateTime.now());
        return vehicleRepository.save(v);
    }

    @PutMapping("/vehicles/{id}/status")
    public Vehicle updateStatus(@PathVariable Integer id, @RequestParam String status) {
        Vehicle v = vehicleRepository.findById(id).orElseThrow();
        v.setStatus(status);
        v.setLastUpdate(LocalDateTime.now());
        return vehicleRepository.save(v);
    }
}
