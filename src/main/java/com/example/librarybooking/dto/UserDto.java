package com.example.librarybooking.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<RoleDto> roles = new HashSet<>();
    private String status;
    private Boolean active;
    private String tone;
    private String initials;
    private String joined;
    private Integer reservations;

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, String password, Set<RoleDto> roles,
                   String status, Boolean active, String tone, String initials, String joined, Integer reservations) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles != null ? roles : new HashSet<>();
        this.status = status;
        this.active = active;
        this.tone = tone;
        this.initials = initials;
        this.joined = joined;
        this.reservations = reservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles != null ? roles : new HashSet<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }

    public Set<RoleDto> getRoleNames() {
        return roles;
    }

    public void setRoleNames(Set<RoleDto> roleNames) {
        this.roles = roleNames != null ? roleNames : new HashSet<>();
    }
}




