package com.example.detectioncar.controller;

import com.example.detectioncar.dto.LoginRequest;
import com.example.detectioncar.dto.RegisterRequest;
import com.example.detectioncar.model.UserInfo;
import com.example.detectioncar.model.UserLogin;
import com.example.detectioncar.repo.UserInfoRepository;
import com.example.detectioncar.repo.UserLoginRepository;
import com.example.detectioncar.util.TokenStore;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserLoginRepository userLoginRepository;
    private final UserInfoRepository userInfoRepository;

    public AuthController(UserLoginRepository userLoginRepository,
                          UserInfoRepository userInfoRepository) {
        this.userLoginRepository = userLoginRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

        UserLogin user = userLoginRepository.findByUsername(req.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(400).body(Map.of("message", "User not found"));
        }

        boolean ok = req.getPassword() != null && req.getPassword().equals(user.getPassword());
        if (!ok) {
            return ResponseEntity.status(400).body(Map.of("message", "Wrong password"));
        }

        String token = TokenStore.createToken(user.getUsername());
        UserInfo info = userInfoRepository.findByUserId(user.getUserId()).orElse(null);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", user.getUsername(),
                "userId", user.getUserId(),
                "familyId", info == null ? null : info.getFamilyId(),
                "message", "ok"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {

        if (userLoginRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "Username already exists"));
        }

        Integer maxUserId = userLoginRepository.findMaxUserId();
        int newUserId = (maxUserId == null ? 1 : maxUserId + 1);

        UserLogin login = new UserLogin();
        login.setUserId(newUserId);
        login.setUsername(req.getUsername());
        login.setPassword(req.getPassword());
        userLoginRepository.save(login);

        UserInfo info = new UserInfo();
        info.setUserId(newUserId);
        info.setName(req.getName());
        info.setFamilyId(req.getFamilyId());
        info.setTel(req.getTel());
        info.setEmail(req.getEmail());
        info.setCarName(req.getCarName());
        info.setState(1);
        userInfoRepository.save(info);

        String token = TokenStore.createToken(req.getUsername());

        return ResponseEntity.ok(Map.of(
                "message", "created",
                "token", token,
                "username", req.getUsername(),
                "userId", newUserId,
                "familyId", req.getFamilyId()
        ));
    }


}
