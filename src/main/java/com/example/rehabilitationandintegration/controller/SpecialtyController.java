package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.model.SpecialtyDto;
import com.example.rehabilitationandintegration.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/specialty")
@RequiredArgsConstructor
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @GetMapping("/all")
    public ResponseEntity<Page<SpecialtyDto>> all(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(specialtyService.all(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(specialtyService.get(id));
    }

//    @PatchMapping("/{id}/update/specialist/{specialistId}")
//    public void change(@PathVariable Long id, @PathVariable Long specialistId) {
//        specialtyService.change(id, specialistId);
//    }
}
