package com.akhianand.springrolejwt.dao;

import com.akhianand.springrolejwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
//    User findByUsername(String username);

    User findByEmail(String email);

    User deleteByEmail(String email);

    User findUserById(Long id);
}