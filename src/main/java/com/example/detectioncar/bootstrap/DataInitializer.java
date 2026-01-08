package com.example.detectioncar.bootstrap;

import com.example.detectioncar.model.UserInfo;
import com.example.detectioncar.model.UserLogin;
import com.example.detectioncar.repo.UserInfoRepository;
import com.example.detectioncar.repo.UserLoginRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 首次启动自动创建一个管理员账号（如果不存在）
 */
@Configuration
public class DataInitializer {

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initAdmin(UserLoginRepository userLoginRepository,
                                      UserInfoRepository userInfoRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (userLoginRepository.findByUsername(adminUsername).isPresent()) {
                return;
            }

            Integer maxUserId = userLoginRepository.findMaxUserId();
            int newUserId = (maxUserId == null ? 0 : maxUserId) + 1;

            UserLogin admin = new UserLogin();
            admin.setUserId(newUserId);
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            userLoginRepository.save(admin);

            // admin 也建一个 user_info（不是必须，但方便 join 查询时不为空）
            UserInfo info = new UserInfo();
            info.setUserId(newUserId);
            info.setName("Administrator");
            info.setCarName("System");
            info.setTel("");
            info.setEmail("");
            info.setState(1);
            info.setFamilyId(null);
            userInfoRepository.save(info);

            System.out.println("=== Admin created ===");
            System.out.println("username: " + adminUsername);
            System.out.println("password: " + adminPassword);
        };
    }
}
