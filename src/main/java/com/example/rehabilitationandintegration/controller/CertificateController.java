package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.model.CertificateDto;
import com.example.rehabilitationandintegration.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificate")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping("/all")
    public ResponseEntity<Page<CertificateDto>> all(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(certificateService.all(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(certificateService.get(id));
    }

    @GetMapping("/specialist/{id}")
    public ResponseEntity<Page<CertificateDto>> specialistCertificates(Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(certificateService.specialistCertificates(pageable, id));
    }

    @PostMapping("/create/specialist/{id}")
    public ResponseEntity<Void> create(@RequestBody @Valid CertificateDto certificateDto, @PathVariable Long id) {
        certificateService.create(certificateDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid CertificateDto certificateDto) {
        certificateService.update(id, certificateDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        certificateService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
