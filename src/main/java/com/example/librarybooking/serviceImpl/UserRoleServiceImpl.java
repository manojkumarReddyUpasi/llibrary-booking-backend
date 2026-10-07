package com.example.librarybooking.serviceImpl;

import com.example.librarybooking.dto.RoleDto;
import com.example.librarybooking.dto.UserDto;
import com.example.librarybooking.entity.Role;
import com.example.librarybooking.entity.User;
import com.example.librarybooking.exception.ResourceNotFoundException;
import com.example.librarybooking.repository.RoleRepository;
import com.example.librarybooking.repository.UserRepository;
import com.example.librarybooking.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + roleId + " not found"));

        user.getRoles().add(role);
        role.getUsers().add(user);

        User savedUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setActive(savedUser.getActive());
        userDto.setRoles(savedUser.getRoles().stream()
                .filter(r -> r != null && r.getName() != null)
                .map(r -> new RoleDto(r.getId(), r.getName()))
                .collect(Collectors.toSet()));
        return userDto;
    }

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setActive(user.getActive());
        if (user.getRoles() != null) {
            Set<RoleDto> roles = user.getRoles().stream()
                    .filter(r -> r != null && r.getName() != null)
                    .map(r -> new RoleDto(r.getId(), r.getName()))
                    .collect(Collectors.toCollection(HashSet::new));
            userDto.setRoles(roles);
        }
        return userDto;
    }

    @Override
    public List<User> removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + roleId + " not found"));

        user.getRoles().remove(role);
        role.getUsers().remove(user);

        userRepository.save(user);

       return userRepository.findAll();


    }
}