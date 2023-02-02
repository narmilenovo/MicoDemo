package com.akhianand.springrolejwt.es.dao;

import com.akhianand.springrolejwt.es.model.UserElk;
import com.akhianand.springrolejwt.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface UserElkDao extends ElasticsearchRepository<UserElk, Long> {

    List<UserElk> searchUserElkByFirstNameOrLastName(String name);
}