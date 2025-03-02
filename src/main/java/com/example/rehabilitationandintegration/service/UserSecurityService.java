package com.example.rehabilitationandintegration.service;


import com.example.rehabilitationandintegration.dao.RoleEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.dao.repository.UserRepository;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.DeletedUserException;
import com.example.rehabilitationandintegration.exception.InactiveUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Загрузка пользователя: {}", username);
        UserEntity userEntity = userRepository.findByUsername(username).
                orElseThrow(()->new UsernameNotFoundException("User not found"));

        UserDetails user = new User(userEntity.getUsername(),userEntity.getPassword(),getRoles(userEntity.getRoles()));
        checkUserStatus(userEntity.getStatus());
        return user;
    }

    private Collection<? extends GrantedAuthority> getRoles(List<RoleEntity> roleEntityList){
        var roles = new ArrayList<GrantedAuthority>();
        for (RoleEntity roleEntity : roleEntityList){
            roles.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getName()));
        }
        return roles;
    }

    private void checkUserStatus(Status status){
        if (status == Status.INACTIVE) {
            throw new InactiveUserException("User is inactive");
        }
        if (status == Status.DELETED) {
            throw new DeletedUserException("User is deleted");
        }
    }
}
