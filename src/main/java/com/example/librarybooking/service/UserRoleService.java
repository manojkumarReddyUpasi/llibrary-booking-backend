package com.example.librarybooking.service;

import com.example.librarybooking.dto.UserDto;
import com.example.librarybooking.entity.User;

import java.util.List;

public interface UserRoleService {
    UserDto assignRoleToUser(Long userId, Long roleId);
    UserDto convertToDto(User user);

    List<User> removeRoleFromUser(Long userId, Long roleId);
}

