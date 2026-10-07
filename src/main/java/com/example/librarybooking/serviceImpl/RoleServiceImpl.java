package com.example.librarybooking.serviceImpl;

import com.example.librarybooking.entity.Role;
import com.example.librarybooking.exception.ResourceNotFoundException;
import com.example.librarybooking.repository.RoleRepository;
import com.example.librarybooking.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;



    @Override
    public Role insertRole(Map<String, String> map) {
        Role role=new Role();
        role.setName(map.get("name"));
        return roleRepository.save(role);
        // Implementation for inserting a role

    }
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));

    }
    @Override
    public Role updateRole(Long id, Map<String, String> map) {
        Role role = getRoleById(id);
        role.setName(map.get("name"));
        return roleRepository.save(role);
    }
@Override
    public void deleteRole(Long id) {
        Role role = getRoleById(id);
        roleRepository.delete(role);
    }
}
