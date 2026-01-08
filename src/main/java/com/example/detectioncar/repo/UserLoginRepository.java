package com.example.detectioncar.repo;

import com.example.detectioncar.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Integer> {

    Optional<UserLogin> findByUsername(String username);

    Optional<UserLogin> findByUserId(Integer userId);

    @Query("select coalesce(max(u.userId),0) from UserLogin u")
    Integer findMaxUserId();

    @Query(value =
            "SELECT l.user_id AS userId, " +
            "       l.username AS username, " +
            "       i.name AS name, " +
            "       i.car_name AS carName, " +
            "       i.tel AS tel, " +
            "       i.email AS email, " +
            "       i.state AS state, " +
            "       i.family_id AS familyId " +
            "FROM user_login l " +
            "LEFT JOIN user_info i ON l.user_id = i.user_id " +
            "WHERE l.username <> 'admin' " +
            "ORDER BY l.user_id",
            nativeQuery = true)
    List<UserRow> findAllUserRows();
}
