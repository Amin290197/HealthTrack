package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.TherapyServicesDto;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import com.example.rehabilitationandintegration.service.TherapyServicesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class TherapyServicesController {
    private final TherapyServicesService therapyServicesService;

    @GetMapping("/all")
    public ResponseEntity<Page<TherapyServicesDto>> all(Pageable pageable, Status status){
        return ResponseEntity.status(HttpStatus.OK).body(therapyServicesService.all(pageable, status));
    }

    @GetMapping("/{therapyId}")
    public ResponseEntity<Page<TherapyServicesDto>> getAllByTherapy(Pageable pageable,@PathVariable Long therapyId) {
        return ResponseEntity.status(HttpStatus.OK).body(therapyServicesService.allByTherapy(pageable, therapyId));
    }

    @PostMapping("/create/{therapyId}")
    public ResponseEntity<Void> create(@PathVariable Long therapyId, @Valid TherapyServicesDto therapyServicesDto){
        therapyServicesService.create(therapyId, therapyServicesDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TherapyServicesDto therapyServicesDto){
        therapyServicesService.update(id, therapyServicesDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        therapyServicesService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/changeStatus")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestBody @Valid ActiveInactiveDto activeInactiveDto){
        therapyServicesService.changeStatus(id, activeInactiveDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
