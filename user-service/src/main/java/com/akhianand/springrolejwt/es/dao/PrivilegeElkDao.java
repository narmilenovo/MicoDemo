package com.akhianand.springrolejwt.es.dao;

import com.akhianand.springrolejwt.es.model.PrivilegeElk;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface PrivilegeElkDao extends ElasticsearchRepository<PrivilegeElk, Long> {

}
