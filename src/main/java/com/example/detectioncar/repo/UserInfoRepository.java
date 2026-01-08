package com.example.detectioncar.repo;

import com.example.detectioncar.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByUserId(Integer userId);

    List<UserInfo> findByFamilyId(Integer familyId);
}
