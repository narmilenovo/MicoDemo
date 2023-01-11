package com.akhianand.springrolejwt.dao;

import com.akhianand.springrolejwt.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeDao extends JpaRepository<Privilege, Long> {

    Privilege findPrivilegeByName(String name);
}
