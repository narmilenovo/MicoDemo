package com.akhianand.springrolejwt.controller;

import com.akhianand.springrolejwt.model.Privilege;
import com.akhianand.springrolejwt.model.User;
import com.akhianand.springrolejwt.service.impl.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")

public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;
    @PreAuthorize("hasAuthority('UM_C')")
    @RequestMapping(value="/privilegeRegister", method = RequestMethod.POST)
    public Privilege savePrivilege(@RequestParam String name)
    {
        return privilegeService.savePrivilege(name);
    }
    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value="/getAllprivileges", method = RequestMethod.GET)
    public List<Privilege> listUser(){
        return privilegeService.findAllPrivileges();
    }
    @PreAuthorize("hasAuthority('UM_D')")
    @DeleteMapping("/deletePrivilegeById/{id}")
    public String deletePrivilegeId(@PathVariable Long id) {
        return privilegeService.deletePrivilegeId(id);
    }
}
