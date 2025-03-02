package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.CertificateEntity;
import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.repository.CertificateRepository;
import com.example.rehabilitationandintegration.dao.repository.SpecialistRepository;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.CertificateMapper;
import com.example.rehabilitationandintegration.model.CertificateDto;
import com.example.rehabilitationandintegration.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final SpecialistRepository specialistRepository;

    @Override
    public Page<CertificateDto> all(Pageable pageable) {
        log.info("Action.log.getAll started");
        Page<CertificateEntity> certificateEntityPage = certificateRepository.findAll(pageable);
        if (certificateEntityPage.isEmpty()) throw new ResourceNotFoundException("Not found certificates");
        List<CertificateDto> certificateDtoList = certificateEntityPage.stream().map(certificateMapper::toDto).toList();
        PageImpl<CertificateDto> certificateDtoPage =
                new PageImpl<>(certificateDtoList, pageable, certificateEntityPage.getTotalElements());
        log.info("Action.log.getAll ended");
        return certificateDtoPage;
    }

    @Override
    public CertificateDto get(Long id) {
        log.info("Action.log.get started for certificate {}", id);
        CertificateEntity certificateEntity = certificateRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Certificate not found"));
        CertificateDto certificateDto = certificateMapper.toDto(certificateEntity);
        log.info("Action.log.get ended for certificate {}", id);
        return certificateDto;
    }

    @Override
    public Page<CertificateDto> specialistCertificates(Pageable pageable, Long specialistId) {
        log.info("Action.log.specialistCertificates started for specialist {}", specialistId);

        SpecialistEntity specialistEntity = specialistRepository.findById(specialistId)
                .orElseThrow(()-> new ResourceNotFoundException("Specialist not found"));
        Page<CertificateEntity> certificateEntityPage = certificateRepository
                .findAllBySpecialist(pageable, specialistEntity);

        if (certificateEntityPage.isEmpty()) throw new ResourceNotFoundException("Not found certificates for specialist");

        List<CertificateDto> certificateDtoList = certificateEntityPage.stream().map(certificateMapper::toDto).toList();
        PageImpl<CertificateDto> certificateDtoPage =
                new PageImpl<>(certificateDtoList, pageable, certificateEntityPage.getTotalElements());
        log.info("Action.log.specialistCertificates ended for specialist {}", specialistId);
        return certificateDtoPage;
    }

    @Override
    public void create(CertificateDto certificateDto, Long specialistId) {
        log.info("Action.createCertificate started for specialist {}", specialistId);
        SpecialistEntity specialistEntity = specialistRepository.findById(specialistId)
                .orElseThrow(()-> new ResourceNotFoundException("Specialist not found"));
        CertificateEntity certificateEntity = certificateMapper.toEntity(certificateDto);
        certificateEntity.setSpecialist(specialistEntity);
        certificateRepository.save(certificateEntity);
        log.info("Action.createCertificate ended for specialist {}", specialistId);
    }

    @Override
    public void update(Long id, CertificateDto certificateDto) {

    }

    @Override
    public void delete(Long id) {
        log.info("Action.deleteCertificate started for certificate {}", id);
        CertificateEntity certificateEntity = certificateRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Certificate not found"));
        if (certificateEntity.getStatus() != Status.DELETED){
            certificateEntity.setStatus(Status.DELETED);
            certificateRepository.save(certificateEntity);
        }
        else throw new RuntimeException("Certificate already deleted");
        log.info("Action.deleteCertificate ended for certificate {}", id);
    }
}
