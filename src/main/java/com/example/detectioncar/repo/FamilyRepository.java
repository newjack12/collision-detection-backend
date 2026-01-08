package com.example.detectioncar.repo;

import com.example.detectioncar.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Integer> {
    List<Family> findByFamilyId(Integer familyId);
}
