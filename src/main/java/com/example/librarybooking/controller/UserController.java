package com.example.librarybooking.controller;

import com.example.librarybooking.dto.ActiveUserDto;
import com.example.librarybooking.dto.UserDto;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.service.UserRoleService;
import com.example.librarybooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")


public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User insertUser(@RequestBody UserDto userDto){
        return userService.insertUser(userDto);
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/needAllUsers")
    public List<User> needAllUsesr() {
        return userService.getUserList();
    }

    @GetMapping("/users/active")
    public List<ActiveUserDto> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        return userService.updateUser(id, userDto);

    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private final UserRoleService userRoleService;

    public UserController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PutMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserDto> assignRoleToUser(@PathVariable Long userId,
                                                    @PathVariable Long roleId) {
        return ResponseEntity.ok(userRoleService.assignRoleToUser(userId, roleId));
    }

    @DeleteMapping("/users/{userId}/roles/{roleId}")
    public ResponseEntity<List<User>> removeRoleFromUser(@PathVariable Long userId,
                                                      @PathVariable Long roleId) {
        return ResponseEntity.ok(userRoleService.removeRoleFromUser(userId, roleId));
    }


}



