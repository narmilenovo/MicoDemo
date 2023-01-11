package com.example.departmentservice.service;

import com.example.departmentservice.entity.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department saveDepartment(Department department) {
        String departNameCaps = department.getDepartName();
        departNameCaps = departNameCaps.substring(0, 1).toUpperCase() + departNameCaps.substring(1).toLowerCase();
        department.setDepartName(departNameCaps);
        return departmentRepository.save(department);
    }

    public Department findDepartmentById(Long departmentId) {
        return departmentRepository.findByDepartmentId(departmentId);
    }

    public Department DepartmentByDepartName(String departName) {
        String caps =  departName.substring(0, 1).toUpperCase() + departName.substring(1).toLowerCase();
        return departmentRepository.findByDepartName(caps);
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }



}
