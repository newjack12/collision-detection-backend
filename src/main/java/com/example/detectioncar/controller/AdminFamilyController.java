package com.example.detectioncar.controller;

import com.example.detectioncar.dto.FamilyCreateRequest;
import com.example.detectioncar.model.Family;
import com.example.detectioncar.model.UserInfo;
import com.example.detectioncar.repo.FamilyRepository;
import com.example.detectioncar.repo.UserInfoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminFamilyController {

    private final FamilyRepository familyRepository;
    private final UserInfoRepository userInfoRepository;

    public AdminFamilyController(FamilyRepository familyRepository, UserInfoRepository userInfoRepository) {
        this.familyRepository = familyRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/families")
    public List<Family> listFamilies() {
        return familyRepository.findAll();
    }

    @PostMapping("/families")
    public ResponseEntity<?> createFamily(@Valid @RequestBody FamilyCreateRequest req) {

        List<Family> exists = familyRepository.findByFamilyId(req.getFamilyId());
        if (exists != null && !exists.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "familyId already exists"));
        }

        Family f = new Family();
        f.setFamilyId(req.getFamilyId());
        f.setName(req.getName());
        familyRepository.save(f);

        return ResponseEntity.ok(Map.of("message", "created"));
    }

    @GetMapping("/families/{familyId}/members")
    public List<UserInfo> listFamilyMembers(@PathVariable Integer familyId) {
        return userInfoRepository.findByFamilyId(familyId);
    }
}
