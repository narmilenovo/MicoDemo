package com.akhianand.springrolejwt.service.impl;

import com.akhianand.springrolejwt.dao.PrivilegeDao;
import com.akhianand.springrolejwt.model.Privilege;
import com.akhianand.springrolejwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeDao privilegeDao;

    public Privilege savePrivilege(String name)
    {
        Privilege privilege = privilegeDao.findPrivilegeByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeDao.save(privilege);
        }
        return privilege;
    }

    public List<Privilege> findAllPrivileges() {
        List<Privilege> list = new ArrayList<>();
        privilegeDao.findAll(Sort.by("id").ascending()).iterator().forEachRemaining(list::add);
        return list;
    }

    public String deletePrivilegeId(Long id) {
        privilegeDao.deleteById(id);
        return "Privilege removed !! " + id;
    }

}
