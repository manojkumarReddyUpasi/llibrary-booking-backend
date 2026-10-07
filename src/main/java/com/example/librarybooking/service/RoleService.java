package com.example.librarybooking.service;

import com.example.librarybooking.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    Role insertRole(Map<String,String> map);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    void deleteRole(Long id);
    Role updateRole(Long id, Map<String,String> map);
}
