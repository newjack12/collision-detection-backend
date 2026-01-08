package com.example.detectioncar.controller;

import com.example.detectioncar.dto.StateRequest;
import com.example.detectioncar.dto.UserCreateRequest;
import com.example.detectioncar.dto.UserUpdateRequest;
import com.example.detectioncar.model.UserInfo;
import com.example.detectioncar.model.UserLogin;
import com.example.detectioncar.repo.UserInfoRepository;
import com.example.detectioncar.repo.UserLoginRepository;
import com.example.detectioncar.repo.UserRow;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private final UserLoginRepository userLoginRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(UserLoginRepository userLoginRepository,
                               UserInfoRepository userInfoRepository,
                               PasswordEncoder passwordEncoder) {
        this.userLoginRepository = userLoginRepository;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<UserRow> listUsers() {
        return userLoginRepository.findAllUserRows();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest req) {

        if (userLoginRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "Username already exists"));
        }

        Integer maxUserId = userLoginRepository.findMaxUserId();
        int newUserId = (maxUserId == null ? 0 : maxUserId) + 1;

        UserLogin login = new UserLogin();
        login.setUserId(newUserId);
        login.setUsername(req.getUsername());
        login.setPassword(passwordEncoder.encode(req.getPassword()));
        userLoginRepository.save(login);

        UserInfo info = new UserInfo();
        info.setUserId(newUserId);
        info.setName(req.getName());
        info.setCarName(req.getCarName());
        info.setTel(req.getTel());
        info.setEmail(req.getEmail());
        info.setFamilyId(req.getFamilyId());
        info.setState(1);
        userInfoRepository.save(info);

        return ResponseEntity.ok(Map.of("message", "created", "userId", newUserId));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest req) {

        UserInfo info = userInfoRepository.findByUserId(userId).orElse(null);
        if (info == null) {

            info = new UserInfo();
            info.setUserId(userId);
            info.setState(1);
        }

        info.setName(req.getName());
        info.setCarName(req.getCarName());
        info.setTel(req.getTel());
        info.setEmail(req.getEmail());
        info.setFamilyId(req.getFamilyId());

        userInfoRepository.save(info);
        return ResponseEntity.ok(Map.of("message", "updated"));
    }

    @PutMapping("/users/{userId}/state")
    public ResponseEntity<?> updateState(@PathVariable Integer userId,
                                         @Valid @RequestBody StateRequest req) {

        if (req.getState() != 0 && req.getState() != 1) {
            return ResponseEntity.status(400).body(Map.of("message", "state must be 0 or 1"));
        }

        UserInfo info = userInfoRepository.findByUserId(userId).orElse(null);
        if (info == null) {
            return ResponseEntity.status(404).body(Map.of("message", "user_info not found"));
        }

        info.setState(req.getState());
        userInfoRepository.save(info);
        return ResponseEntity.ok(Map.of("message", "state updated"));
    }

    @PutMapping("/users/{userId}/password")
    public ResponseEntity<?> resetPassword(@PathVariable Integer userId,
                                          @RequestBody Map<String, String> body) {

        String newPassword = body.get("password");
        if (newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.status(400).body(Map.of("message", "password required"));
        }

        UserLogin login = userLoginRepository.findByUserId(userId).orElse(null);
        if (login == null) {
            return ResponseEntity.status(404).body(Map.of("message", "user_login not found"));
        }

        login.setPassword(passwordEncoder.encode(newPassword));
        userLoginRepository.save(login);
        return ResponseEntity.ok(Map.of("message", "password updated"));
    }
}
