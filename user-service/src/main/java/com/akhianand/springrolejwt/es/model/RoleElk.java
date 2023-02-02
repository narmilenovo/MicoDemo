package com.akhianand.springrolejwt.es.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(indexName = "roles")
@Setting(settingPath = "es-config/elastic-analyzer.json")
public class RoleElk {
    private Long id;
    @Field(type = FieldType.Text,analyzer = "autocomplete_index",searchAnalyzer = "autocomplete_search")
    private String name;
    private String plantName;
    private String description;
    private List<PrivilegeElk> privileges;
    private boolean status;
    private Date createdAt;
    @Field(type=FieldType.Date)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PrivilegeElk> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeElk> privileges) {
        this.privileges = privileges;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public RoleElk(Long id, String name, String plantName, String description, List<PrivilegeElk> privileges, boolean status) {
        this.id = id;
        this.name = name;
        this.plantName = plantName;
        this.description = description;
        this.privileges = privileges;
        this.status = status;
    }

    public RoleElk() {
    }
}
