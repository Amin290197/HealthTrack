package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.RoleEntity;
import com.example.rehabilitationandintegration.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(Role name);
}
