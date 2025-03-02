package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.dao.repository.UserRepository;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.AlreadyException;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.UserMapper;
import com.example.rehabilitationandintegration.model.UserDto;
import com.example.rehabilitationandintegration.specification.UserFilter;
import com.example.rehabilitationandintegration.model.request.UserChangePasswordDto;
import com.example.rehabilitationandintegration.model.request.UserUpdateDto;
import com.example.rehabilitationandintegration.model.response.UserResponseDto;
import com.example.rehabilitationandintegration.service.MailService;
import com.example.rehabilitationandintegration.service.UserService;
import com.example.rehabilitationandintegration.utility.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


    @Override
    public Page<UserResponseDto> getAll(Pageable pageable, UserFilter userFilter) {
        log.info("Action.log.getAll started");

        var specification = Specification.where(userFilter);
        Page<UserEntity> userEntityPage = userRepository.findAll(specification, pageable);
        if (userEntityPage.isEmpty()) {
            throw new ResourceNotFoundException("Not found any user");
        }
        List<UserResponseDto> userDtoList = userEntityPage
                .stream()
                .map(userMapper::toResponse)
                .toList();
        Page<UserResponseDto> userDtoPage = new PageImpl<>(userDtoList, pageable, userEntityPage.getTotalElements());

        log.info("Action.log.getAll ended");
        return userDtoPage;
    }

    @Override
    public UserDto get(Long id) {
        log.info("Action.log.get started for User {}", id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserDto userDto = userMapper.toDto(userEntity);
        log.info("Action.log.get ended for User {}", id);
        return userDto;
    }

    @Override
    public void update(Long id, UserUpdateDto userDto) {
        log.info("Action.log.update started for user {}", id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        userMapper.forUpdate(userDto, userEntity);
        userRepository.save(userEntity);
        log.info("Action.log.update ended for user {}", id);
    }

    @Override
    public void changeStatus(Long id, Status status) {
        log.info("Action.changeUserStatus started for user {}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for change status"));
        if (status == user.getStatus())
            throw new IllegalArgumentException("Same status provided");
        user.setStatus(status);
        userRepository.save(user);
        log.info("Action.changeUserStatus ended for user {}", id);
    }

    @Override
    public void activateUser(Long id) {
        log.info("Action.activateUser started for user {}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getStatus() != Status.ACTIVE) {
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
            mailService.sendActiveEmail(user);
            log.info("Action.activateUser ended for user {}", id);
        } else throw new AlreadyException("User already active");
        log.info("Action.activateUser ended for user {}", id);
    }

    @Override
    public void changePassword(Long id, UserChangePasswordDto change) {
        log.info("Action.log.changePassword started for user {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for change password"));
        if (passwordEncoder.matches(change.getOldPassword(), user.getPassword())
                && change.getNewPassword().equals(change.getNewPasswordConfirm())) {
            user.setPassword(passwordEncoder.encode(change.getNewPassword()));
            userRepository.save(user);
            log.info("Action.log.changePassword ended for user {}", user.getId());
        } else {
            throw new IllegalArgumentException("Failed to change password");
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Action.log.delete started for user {}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getStatus() != Status.DELETED) {
            user.setStatus(Status.DELETED);
            userRepository.save(user);
        }
        log.info("Action.log.delete ended for user {}", id);
    }
}
