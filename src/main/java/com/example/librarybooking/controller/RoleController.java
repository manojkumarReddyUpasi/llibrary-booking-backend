package com.example.librarybooking.controller;

import com.example.librarybooking.dto.UserDto;
import com.example.librarybooking.entity.Role;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.service.RoleService;
import com.example.librarybooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("/roles")
    public Role inserRole(@RequestBody Map<String,String> map) {
        return roleService.insertRole(map);
    }
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }
    @PutMapping("/roles/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Map<String,String> map) {
        return roleService.updateRole(id, map);
    }
    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Long id) {
        // Implement delete role logic here
        roleService.deleteRole(id);

    }
}
