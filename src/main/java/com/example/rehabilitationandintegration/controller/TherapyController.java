package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import com.example.rehabilitationandintegration.model.response.TherapyDto;
import com.example.rehabilitationandintegration.service.TherapyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/therapy")
@RequiredArgsConstructor
public class TherapyController {
    private final TherapyService therapyService;

    @GetMapping("/all")
    public ResponseEntity<Page<TherapyDto>> getTherapies(Pageable pageable, @RequestParam Status status) {
        return ResponseEntity.status(HttpStatus.OK).body(therapyService.getAll(pageable,status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TherapyDto> get(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(therapyService.get(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createTherapy(@RequestBody @Valid TherapyDto therapyDto){
        therapyService.create(therapyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> updateTherapy(@RequestBody @Valid TherapyDto therapyDto, @PathVariable Long id){
        therapyService.update(therapyDto, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTherapy(@PathVariable Long id){
        therapyService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/changeStatus")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestBody @Valid ActiveInactiveDto status){
        therapyService.changeStatus(id, status);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
