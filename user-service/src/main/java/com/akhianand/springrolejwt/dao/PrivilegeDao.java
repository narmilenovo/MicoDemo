package com.akhianand.springrolejwt.dao;

import com.akhianand.springrolejwt.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrivilegeDao extends JpaRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege> {

    Privilege findPrivilegeByName(String name);
}
