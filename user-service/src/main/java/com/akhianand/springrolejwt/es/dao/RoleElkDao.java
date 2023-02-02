package com.akhianand.springrolejwt.es.dao;

import com.akhianand.springrolejwt.es.model.RoleElk;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleElkDao extends ElasticsearchRepository<RoleElk, Long> {

}