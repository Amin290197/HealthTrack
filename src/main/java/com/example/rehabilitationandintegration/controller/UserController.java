package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.UserDto;
import com.example.rehabilitationandintegration.specification.UserFilter;
import com.example.rehabilitationandintegration.model.request.UserChangePasswordDto;
import com.example.rehabilitationandintegration.model.request.UserUpdateDto;
import com.example.rehabilitationandintegration.model.response.UserResponseDto;
import com.example.rehabilitationandintegration.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/all")
    public ResponseEntity<Page<UserResponseDto>> all(Pageable pageable, @RequestBody UserFilter userFilter) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(pageable, userFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.get(id));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userDto) {
        userService.update(id, userDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/changeStatus")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestBody Status status) {
        userService.changeStatus(id, status);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<Void> changePassword(Long id,
                                               @RequestBody @Valid UserChangePasswordDto userChangePasswordDto) {
        userService.changePassword(id, userChangePasswordDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id){
        userService.activateUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}