package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.SpecialtyEntity;
import com.example.rehabilitationandintegration.dao.repository.SpecialistRepository;
import com.example.rehabilitationandintegration.dao.repository.SpecialtyRepository;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.SpecialistMapper;
import com.example.rehabilitationandintegration.mapper.SpecialtyMapper;
import com.example.rehabilitationandintegration.model.SpecialtyDto;
import com.example.rehabilitationandintegration.service.SpecialistService;
import com.example.rehabilitationandintegration.service.SpecialtyService;
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
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;
    private final SpecialistRepository specialistRepository;

    @Override
    public Page<SpecialtyDto> all(Pageable pageable) {
        log.info("Action.all.specialties started");
        Page<SpecialtyEntity> specialtyEntityPage = specialtyRepository.findAll(pageable);
        if (specialtyEntityPage.isEmpty()) throw new ResourceNotFoundException("Not found specialties");
        List<SpecialtyDto> specialtyDtoList = specialtyEntityPage.stream().map(specialtyMapper::toDto).toList();
        PageImpl<SpecialtyDto> specialtyDtoPage =
                new PageImpl<>(specialtyDtoList, pageable, specialtyEntityPage.getTotalElements());
        log.info("Action.all.specialties ended");
        return specialtyDtoPage;
    }

    @Override
    public SpecialtyDto get(Long id) {
        log.info("Action.get.specialty started for id {}", id);
        SpecialtyEntity specialtyEntity = specialtyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Specialty not found"));
        SpecialtyDto specialtyDto = specialtyMapper.toDto(specialtyEntity);
        log.info("Action.get.specialty ended for id {}", id);
        return specialtyDto;
    }

    @Override
    public void change(Long id, Long specialtyId) {
        log.info("Action.log.addSpecialty started for Specialty {}", id);
        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Specialist not found"));
        SpecialtyEntity specialtyToAdd = specialtyRepository.findById(specialtyId)
                .orElseThrow(()-> new ResourceNotFoundException("Specialty not found"));
        specialistEntity.setSpecialty(specialtyToAdd);
        specialistRepository.save(specialistEntity);
        log.info("Action.log.addSpecialty ended for Specialty {}", id);
    }

}
