package com.example.librarybooking.serviceImpl;

import com.example.librarybooking.dto.ActiveUserDto;
import com.example.librarybooking.dto.RoleDto;
import com.example.librarybooking.dto.UserDto;
import com.example.librarybooking.entity.Role;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.exception.ResourceNotFoundException;
import com.example.librarybooking.repository.RoleRepository;
import com.example.librarybooking.repository.UserRepository;
import com.example.librarybooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User insertUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setActive(userDto.getActive() != null ? userDto.getActive() : true);
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            Set<Role> roles = userDto.getRoles().stream()
                    .map(roleDto -> roleRepository.findByName(roleDto.getName())
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleDto.getName())))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        return userRepository.save(user);
    }

    public List<String> needAllUsesr() {
        return userRepository.getAllUsers();
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public List<ActiveUserDto> getActiveUsers() {
        return userRepository.findByActiveTrue().stream()
                .map(user -> new ActiveUserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getActive()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setActive(user.getActive());
        if (user.getRoles() != null) {
            userDto.setRoles(user.getRoles().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toSet()));
        }
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getActive() != null) {
            user.setActive(userDto.getActive());
        }



        userDto.getRoles().forEach(e ->{
            Optional<Role> hari=roleRepository.findByName(e.getName());

            if(hari.isPresent()){
                user.getRoles().add(hari.get());
            }
        });

        userRepository.save(user);
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(user.getId());
        updatedUserDto.setName(user.getName());
        updatedUserDto.setEmail(user.getEmail());
        updatedUserDto.setPassword(user.getPassword());
        updatedUserDto.setActive(user.getActive());
        updatedUserDto.setRoles(user.getRoles().stream().map(role -> new RoleDto(role.getId(), role.getName())).collect(Collectors.toSet()));
        return updatedUserDto;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
    }
}
