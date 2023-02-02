package com.akhianand.springrolejwt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.akhianand.springrolejwt.model.group;
@Repository
public interface GroupDao extends JpaRepository<group, Long> {

	group findByGroupName(String groupName);

	group findByGroupId(Long groupId);



}
