package com.akhianand.springrolejwt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


public class RoleDto {

    private String name;

    private String description;

    private String plantName;

    private String[] privileges;

    private boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String[] privileges) {
        this.privileges = privileges;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RoleDto() {
    }

    public RoleDto(String name, String description, String plantName, String[] privileges, boolean status) {
        this.name = name;
        this.description = description;
        this.plantName = plantName;
        this.privileges = privileges;
        this.status = status;
    }
}
