package com.akhianand.springrolejwt.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.akhianand.springrolejwt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.akhianand.springrolejwt.dao.GroupDao;
import com.akhianand.springrolejwt.dao.UserDao;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class GroupService {
@Autowired
private UserDao userDao;
@Autowired 
private GroupDao groupDao;


public List<group> findAllGroup(){
	List<group> list=new ArrayList<group>();
	groupDao.findAll(Sort.by("groupId").ascending()).iterator().forEachRemaining(list::add);
	return list;
}

public group findByName(String groupName) {
	group group = groupDao.findByGroupName(groupName);
    return group;
}

public group save(grouptDto group) {
	group newGroup = new group();
	newGroup.setGroupName(group.getGroupName());
    newGroup.setGroupDescription(group.getGroupDescription());
    newGroup.setPlant(group.getPlant());
    newGroup.setStatus(group.isStatus());
    newGroup.setUser(getUsers(group.getUser()));
    return groupDao.save(newGroup);

}

public group edit(Long groupId, grouptDto editgroup)
{
    group updateGroup= groupDao.findByGroupId(groupId);
    updateGroup.setGroupName(editgroup.getGroupName());
    updateGroup.setPlant(editgroup.getPlant());
    updateGroup.setUser(getUsers(editgroup.getUser()));
    updateGroup.setGroupDescription(editgroup.getGroupDescription());
    updateGroup.setStatus(editgroup.isStatus());
    return groupDao.save(updateGroup);
}
public List<group> updateBulkStatusGroupId(List<Long> id)
{
    List<group> existinggroup = groupDao.findAllById(id);
    for (group group: existinggroup)
    {
        group.setStatus(group.isStatus() == true ? false : true);
    }
    groupDao.saveAll(existinggroup);
    return existinggroup;
}

private Set<User> getUsers(String[] User){
    Set<User> userGroup = new HashSet<>();
    for(String email : User) {
    	userGroup.add(userDao.findByEmail(email));
    }
    return userGroup;
}

    public group findById(Long groupId) {
        group group = groupDao.findByGroupId(groupId);
        return group;
    }


    public List<GroupUserDto> getAllGroupUserEmail() {
        return groupDao.findAll(Sort.by("groupId").ascending()).stream().map(this::convertGroupUserDto).collect(Collectors.toList());
    }


    private GroupUserDto convertGroupUserDto (group Group)
    {
        GroupUserDto groupUserDto = new GroupUserDto();
        groupUserDto.setGroupId(Group.getGroupId());
        groupUserDto.setGroupName(Group.getGroupName());
        groupUserDto.setPlant(Group.getPlant());
        groupUserDto.setGroupDescription(Group.getGroupDescription());
        groupUserDto.setStatus(Group.isStatus());
        groupUserDto.setUsers(Group.getUser().stream().map(User::getEmail).collect(Collectors.toSet()));
        groupUserDto.setCountUsers(Group.getUser().stream().count());
        return groupUserDto;
    }
    @Transactional
    public void deleteBatchGroup(List<Long> id){
    	
	        	   List<group> groupList = groupDao.findAllById(id);
	        	  try {
	        		  groupDao.deleteAll(groupList);
	        	  }
	        	  catch (Exception e) {
					
				}
    }

}
