package com.akhianand.springrolejwt.controller;

import com.akhianand.springrolejwt.model.*;
import com.akhianand.springrolejwt.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")

public class RoleController {

    @Autowired
    private RoleService roleService;
    @PreAuthorize("hasAuthority('UM_C')")
    @RequestMapping(value="/roleRegister", method = RequestMethod.POST)
    public Role saveRole(@RequestBody RoleDto role)
    {
        return roleService.saveRole(role);
    }
    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value="/getAllRoles", method = RequestMethod.GET)
    public List<Role> listUser(){
        return roleService.findAllRoles();
    }

    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value="/getAllRoleUserEmail", method = RequestMethod.GET)
    public List<RoleUserDto> getAllRoleUserEmail(){
        return roleService.getAllRoleUserEmail();
    }

    @PreAuthorize("hasAuthority('UM_U')")
    @RequestMapping(value="/updateRoles", method = RequestMethod.PUT)
    public ResponseEntity<Role> updateRole(@RequestParam Long id,@RequestBody RoleDto roles)
    {
        try {
            Role role= roleService.updateRoles(id,roles);
            return ResponseEntity.ok(role);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PreAuthorize("hasAuthority('UM_U')")
    @PutMapping("/updateRoleStatusById/{id}")
    public Role updateRoleStatusId(@PathVariable Long id, @RequestParam  boolean status)
    {
        return roleService.updateStatusUsingRoleId(id,status);
    }

    @PreAuthorize("hasAuthority('UM_U')")
    @PutMapping("/updateBulkStatusRoleId/{id}")
    public List<Role> updateBulkStatusRoleId(@PathVariable List<Long> id)
    {
        return roleService.updateBulkStatusRoleId(id);
    }

    @PreAuthorize("hasAuthority('UM_D')")
    @RequestMapping(value="/deleteRole/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Role> deleteRole(@PathVariable Long id) {
                    roleService.deleteRoleId(id);
            return new ResponseEntity<>(HttpStatus.OK);

    }
    @PreAuthorize("hasAuthority('UM_D')")
    @DeleteMapping("/deleteBatchRole/{id}")
    public void deleteBatchRole(@PathVariable List<Long> id) {
          roleService.deleteBatchRole(id);
    }
    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value="/getRoleId/{id}", method = RequestMethod.GET)
    public Optional<Role> getId(@PathVariable Long id)
    {
        return roleService.getId(id);
    }

    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value="/getAllRolesTrue", method = RequestMethod.GET)
    public List<Role> listRoleTrue(){
        return roleService.findALlStatusTrue();
    }

}
