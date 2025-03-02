package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.UserDto;
import com.example.rehabilitationandintegration.specification.UserFilter;
import com.example.rehabilitationandintegration.model.request.UserChangePasswordDto;
import com.example.rehabilitationandintegration.model.request.UserUpdateDto;
import com.example.rehabilitationandintegration.model.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    Page<UserResponseDto> getAll(Pageable pageable, UserFilter userFilter);

    UserDto get(Long id);

    void update(Long id, UserUpdateDto userDto);

    void delete(Long id);

    void changePassword(Long id, UserChangePasswordDto change);

    void changeStatus(Long id, Status status);

    void activateUser(Long id);
}
