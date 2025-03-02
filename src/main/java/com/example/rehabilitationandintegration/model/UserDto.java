package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.Gender;
import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class UserDto implements UserDetails {

    private Long id;

    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String name;
    private String surname;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Language language;
    private LocalDateTime registrationDate;
    private String note;
//
//    private PatientEntity patient;
//
//    private ChildEntity child;

//    private List<RoleEntity> roles;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
