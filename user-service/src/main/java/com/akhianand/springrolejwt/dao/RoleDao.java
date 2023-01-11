package com.akhianand.springrolejwt.dao;

import com.akhianand.springrolejwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
    Role findRoleById(Long id);
    void deleteById(Long id);

    List<Role> findAllByStatusIsTrue();
}