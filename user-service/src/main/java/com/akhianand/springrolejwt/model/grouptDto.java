package com.akhianand.springrolejwt.model;

import lombok.Data;

@Data
public class grouptDto {
	 private Long groupId;
	 private String groupName;
	 private String plant;
	 private String groupDescription;
	 private boolean status;
	 
	 private String[] User;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String[] getUser() {
		return User;
	}

	public void setUser(String[] user) {
		User = user;
	}
	 
}
