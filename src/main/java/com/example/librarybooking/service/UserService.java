package com.example.librarybooking.service;

import com.example.librarybooking.dto.ActiveUserDto;
import com.example.librarybooking.dto.BookDto;
import com.example.librarybooking.dto.UserDto;
import com.example.librarybooking.entity.Book;
import com.example.librarybooking.entity.User;

import java.util.List;

public interface UserService {
    User insertUser(UserDto userDto);
    public List<String> needAllUsesr();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    List<User> getUserList();
    List<ActiveUserDto> getActiveUsers();
}
