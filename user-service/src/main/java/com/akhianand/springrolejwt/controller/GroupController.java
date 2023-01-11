package com.akhianand.springrolejwt.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.akhianand.springrolejwt.dao.GroupDao;
import com.akhianand.springrolejwt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.akhianand.springrolejwt.service.impl.GroupService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class GroupController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupDao groupDao;
	@PreAuthorize("hasAuthority('UM_R')")
	@RequestMapping(value="/getAll", method = RequestMethod.GET)
	public List<group> listGroup(){
	        return groupService.findAllGroup();
	    }

	@PreAuthorize("hasAuthority('UM_R')")
	@RequestMapping(value="/getAllGroupUserEmail", method = RequestMethod.GET)
	public List<GroupUserDto> getAllGroupUserEmail() {
		return groupService.getAllGroupUserEmail();
	}

	@PreAuthorize("hasAuthority('UM_R')")
	@RequestMapping(value="/groupById/{groupId}", method = RequestMethod.GET)
	public group groupById(@PathVariable Long groupId){
		return groupService.findById(groupId);
	}


	@PreAuthorize("hasAuthority('UM_C')")
	@RequestMapping(value="/savegroup", method = RequestMethod.POST)
	    public group saveGroup(@RequestBody grouptDto group){
	        return groupService.save(group);
	    }

	@PreAuthorize("hasAuthority('UM_D')")
	@DeleteMapping(value="/deletegroup/{groupName}")
	public ResponseEntity<HttpStatus> deletegroup(@PathVariable String groupName) {
		try {

			group DeleteGroup = groupService.findByName(groupName);
			groupDao.delete(DeleteGroup);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PreAuthorize("hasAuthority('UM_D')")
    @DeleteMapping("/deleteByBatchGroup/{id}")
    public void deleteBatchGroup(@PathVariable List<Long> id) {
    	groupService.deleteBatchGroup(id);
    }

	@PreAuthorize("hasAuthority('UM_U')")
    @PutMapping("/updateBulkStatusUpdateId/{id}")
    public List<group> updateBulkStatusUpdateId(@PathVariable List<Long> id)
    {
        return groupService.updateBulkStatusGroupId(id);
    }

	@PreAuthorize("hasAuthority('UM_U')")
	@PutMapping(value="/editgroup")
	public ResponseEntity<group> editGroup(Long groupId, @RequestBody grouptDto editgroup)
	{

		try {
			group updateGroup= groupService.edit(groupId,editgroup);
			return ResponseEntity.ok(updateGroup);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
