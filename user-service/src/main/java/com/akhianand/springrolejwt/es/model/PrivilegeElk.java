package com.akhianand.springrolejwt.es.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(indexName = "privileges")
public class PrivilegeElk {
    private Long id;
    private String name;
    private List<RoleElk> roles;
    private Date createdAt;
    @Field(type= FieldType.Date)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    public PrivilegeElk(String name) {
        this.name=name;
    }

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

    public PrivilegeElk(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PrivilegeElk(Long id, String name, List<RoleElk> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public PrivilegeElk() {
    }

}
