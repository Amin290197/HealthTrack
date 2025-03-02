package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.request.SpecialistCreateUpdateDto;
import com.example.rehabilitationandintegration.model.response.PatientResponseDto;
import com.example.rehabilitationandintegration.model.response.SpecialistAllResponseDto;
import com.example.rehabilitationandintegration.model.response.SpecialistResponseDto;
import com.example.rehabilitationandintegration.model.response.UserResponseDto;
import com.example.rehabilitationandintegration.service.SpecialistService;
import com.example.rehabilitationandintegration.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/specialist")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistService specialistService;


    @GetMapping("/all")
    public ResponseEntity<Page<SpecialistAllResponseDto>> getAllSpecialists(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(specialistService.all(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistResponseDto> getSpecialist(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(specialistService.get(id));
    }

    @GetMapping("/{id}/allPatients")
    public ResponseEntity<Page<UserResponseDto>> getSpecialistPatient(Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(specialistService.getSpecialistPatients(pageable, id));
    }

    @GetMapping("/specialty/{id}")
    public ResponseEntity<Page<SpecialistAllResponseDto>> getBySpecialty(Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(specialistService.getSpecialty(pageable, id));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> updateSpecialist(@PathVariable Long id,
                                 @RequestBody @Validated({ValidationGroups.Update.class})
                                 SpecialistCreateUpdateDto specialistDto) {
        specialistService.update(id, specialistDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createSpecialist(@RequestBody @Validated({ValidationGroups.Create.class})
                                 SpecialistCreateUpdateDto specialistDto) {
        specialistService.create(specialistDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long id) {
        specialistService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/changeStatus")
    public ResponseEntity<Void> changeSpecialistStatus(@PathVariable Long id, @RequestBody Status status) {
        specialistService.changeStatus(id, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
