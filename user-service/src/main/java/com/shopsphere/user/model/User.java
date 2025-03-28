package com.shopsphere.user.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopsphere.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<String> roles;
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
} 