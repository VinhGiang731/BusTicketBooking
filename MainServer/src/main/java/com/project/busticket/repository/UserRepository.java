package com.project.busticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    boolean existsByUserName(String userName);

    boolean existsByUserId(String userId);

    List<Users> findByUserName(String userName);
}
