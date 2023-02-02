package com.akhianand.springrolejwt.model;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "groups")
@Data
public class group {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="group_id")
    private Long groupId;

	@Column(name="group_name" )
    private String groupName;
	
	@Column(name="group_plant")
    private String plant;
	
    @Column(name="group_desc" )
    private String groupDescription;
    
    @Column(name="group_status")
    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_groups",
            joinColumns = {
                    @JoinColumn(name = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "group_id") })
    private Set<User> User = new HashSet<>();


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

    public Set<com.akhianand.springrolejwt.model.User> getUser() {
        return User;
    }

    public void setUser(Set<com.akhianand.springrolejwt.model.User> user) {
        User = user;
    }

    public group(Long groupId, String groupName, String plant, String groupDescription, boolean status, Set<com.akhianand.springrolejwt.model.User> user) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.plant = plant;
        this.groupDescription = groupDescription;
        this.status = status;
        User = user;
    }

    public group() {
    }
}
