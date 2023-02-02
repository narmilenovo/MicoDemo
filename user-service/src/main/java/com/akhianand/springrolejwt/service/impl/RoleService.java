package com.akhianand.springrolejwt.service.impl;

import com.akhianand.springrolejwt.dao.PrivilegeDao;
import com.akhianand.springrolejwt.dao.RoleDao;
import com.akhianand.springrolejwt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }

    public Role saveRole(RoleDto role)
    {
        Role newRole = new Role();
        newRole.setName(role.getName());
        newRole.setDescription(role.getDescription());
        newRole.setPlantName(role.getPlantName());
        newRole.setPrivileges(getPrivileges(role.getPrivileges()));
        newRole.setStatus(role.isStatus());
        return roleDao.save(newRole);
    }
    private Set<Privilege> getPrivileges(String[] privileges){
        Set<Privilege> userRoles = new HashSet<>();
        for(String privilege : privileges) {
            userRoles.add(privilegeDao.findPrivilegeByName(privilege));
        }
        return userRoles;
    }

    public List<Role> findAllRoles() {
        List<Role> list = new ArrayList<>();
        roleDao.findAll(Sort.by("id").ascending()).iterator().forEachRemaining(list::add);
        return list;
    }

    public Role updateRoles(Long id, RoleDto roles) {

            Optional<Role> check = roleDao.findById(id);
            if (check.isPresent()) {
                Role newRole = new Role();
                newRole.setId(id);
                newRole.setName(roles.getName());
                newRole.setDescription(roles.getDescription());
                newRole.setPlantName(roles.getPlantName());
                newRole.setPrivileges(getPrivileges(roles.getPrivileges()));
                newRole.setStatus(roles.isStatus());

                return roleDao.save(newRole);
            } else
                return null;

    }
    public List<Role> updateBulkStatusRoleId(List<Long> id)
    {
        List<Role> existingRole = roleDao.findAllById(id);
        for (Role role: existingRole)
        {
        	role.setStatus(role.isStatus() == true ? false : true);
        }
        roleDao.saveAll(existingRole);
        return existingRole;
    }
    public Role updateStatusUsingRoleId(Long id,boolean status)
    {
        Role existingRole = roleDao.findRoleById(id);
        existingRole.setStatus(status);
        return roleDao.save(existingRole);
    }

    @Transactional
    public String deleteRoleId(Long id) {
         roleDao.deleteById(id);
         return "hjsgchjiszx";
    }
    
    @Transactional
    public void deleteBatchRole(List<Long> roleIdList){
    	
	        	   List<Role> roleList = roleDao.findAllById(roleIdList);
	        	  try {
	        	    roleDao.deleteAll(roleList);
	        	  }
	        	  catch (Exception e) {
					
				}
    }

    public Optional<Role> getId(Long id)
    {
        return roleDao.findById(id);
    }

    public List<Role> findALlStatusTrue()
    {
       return roleDao.findAllByStatusIsTrue();
    }

    public List<RoleUserDto> getAllRoleUserEmail() {
        return roleDao.findAll(Sort.by("id").ascending()).stream().map(this::convertRoleUserDto).collect(Collectors.toList());
    }


    private RoleUserDto convertRoleUserDto (Role role)
    {
        RoleUserDto roleUserDto = new RoleUserDto();
        roleUserDto.setId(role.getId());
        roleUserDto.setName(role.getName());
        roleUserDto.setDescription(role.getDescription());
        roleUserDto.setPlantName(role.getPlantName());
        roleUserDto.setStatus(role.isStatus());
        roleUserDto.setUsers(role.getUsers().stream().map(User::getEmail).collect(Collectors.toSet()));
        roleUserDto.setCountUsers(role.getUsers().stream().count());
        return roleUserDto;
    }


}
