package com.akhianand.springrolejwt.es.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(indexName = "users")
@Setting(settingPath = "es-config/elastic-analyzer.json")
public class UserElk {

    private Long id;
    private String email;
//    @JsonIgnore
    private String password;
    private String phone;
    @Field(type = FieldType.Text,analyzer = "autocomplete_index",searchAnalyzer = "autocomplete_search")
    private String firstName;
    private String lastName;
    private String business;
    private String departName;
    private String plant;
    private String plantCode;
    private List<RoleElk> roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public List<RoleElk> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleElk> roles) {
        this.roles = roles;
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

    public UserElk(Long id, String email, String password, String phone, String firstName, String lastName, String business, String departName, String plant, String plantCode, List<RoleElk> roles, boolean status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.business = business;
        this.departName = departName;
        this.plant = plant;
        this.plantCode = plantCode;
        this.roles = roles;
        this.status = status;
    }

    public UserElk() {
    }
}