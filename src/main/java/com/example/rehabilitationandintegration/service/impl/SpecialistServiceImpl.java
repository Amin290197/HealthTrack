package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.*;
import com.example.rehabilitationandintegration.dao.repository.*;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.SpecialistMapper;
import com.example.rehabilitationandintegration.mapper.UserMapper;
import com.example.rehabilitationandintegration.model.request.SpecialistCreateUpdateDto;
import com.example.rehabilitationandintegration.model.response.SpecialistAllResponseDto;
import com.example.rehabilitationandintegration.model.response.SpecialistResponseDto;
import com.example.rehabilitationandintegration.model.response.UserResponseDto;
import com.example.rehabilitationandintegration.service.SpecialistService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final SpecialtyRepository specialtyRepository;
    private final SpecialistMapper specialistMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Page<SpecialistAllResponseDto> all(Pageable pageable) {
        log.info("Action.log.getAll started");
        Page<SpecialistEntity> specialistEntity = specialistRepository.findAll(pageable);
        if (specialistEntity.isEmpty()) throw new ResourceNotFoundException("Not found specialists");
        List<SpecialistAllResponseDto> specialistDto = specialistEntity.stream()
                .map(specialistMapper::toAllDto).toList();
        PageImpl<SpecialistAllResponseDto> specialistDtoPage =
                new PageImpl<>(specialistDto, pageable, specialistEntity.getTotalElements());
        log.info("Action.log.getAll ended");
        return specialistDtoPage;
    }

    @Override
    public SpecialistResponseDto get(Long id) {
        log.info("Action.log.get started for specialist {}", id);
        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        SpecialistResponseDto specialistDto = specialistMapper.toResponseDto(specialistEntity);
        log.info("Action.log.get ended for specialist {}", id);
        return specialistDto;
    }

    @Override
    public Page<SpecialistAllResponseDto> getSpecialty(Pageable pageable, Long specialtyId) {
        log.info("Action.log.getSpecialty started for specialty {}", specialtyId);
        SpecialtyEntity specialtyEntity = specialtyRepository.findById(specialtyId)
                .orElseThrow(()-> new ResourceNotFoundException("Specialty not found"));
        Page<SpecialistEntity> specialistEntityPage = specialistRepository
                .findAllByStatusAndSpecialty(pageable,Status.ACTIVE, specialtyEntity);
        if (specialistEntityPage.isEmpty()) throw new ResourceNotFoundException("Not found any specialist");
        List<SpecialistAllResponseDto> specialistDtoList = specialistEntityPage.stream()
                .map(specialistMapper::toAllDto).toList();
        PageImpl<SpecialistAllResponseDto> specialistResponseDtoPage =
                new PageImpl<>(specialistDtoList, pageable, specialistEntityPage.getTotalElements());
        log.info("Action.log.getSpecialty ended for specialty {}", specialtyId);
        return specialistResponseDtoPage;
    }

    @Override
    public Page<UserResponseDto> getSpecialistPatients(Pageable pageable, Long id) {
        log.info("Action.log.getSpecialistPatients started for specialist {}", id);

        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));

        List<UserEntity> userEntityList = specialistEntity.getUser();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userResponseDtoList.add(userMapper.toResponse(userEntity));
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), userEntityList.size());
        List<UserResponseDto> paginatedList = userResponseDtoList.subList(start, end);

        log.info("Action.log.getSpecialistPatients ended for specialist {}", id);
        return new PageImpl<>(paginatedList, pageable, userEntityList.size());
    }


    @Override
    public void create(SpecialistCreateUpdateDto specialistDto) {
        log.info("Action.log.createSpecialist started for specialist {}", specialistDto.getName());
        SpecialtyEntity specialtyEntity = specialtyRepository.findByName(specialistDto.getSpecialty())
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found"));
        SpecialistEntity specialistEntity = specialistMapper.toEntityFromUpdateAndCreate(specialistDto);
        specialistEntity.setSpecialty(specialtyEntity);
        SpecialistEntity saveCheck = specialistRepository.save(specialistEntity);
        if (saveCheck == null) throw new RuntimeException("Specialist not saved");
        log.info("Action.log.createSpecialist started for specialist {}", specialistDto.getId());
    }

    @Override
    public void update(Long id, SpecialistCreateUpdateDto specialistDto) {
        log.info("Action.log.updateSpecialist started for specialist {}", specialistDto.getId());
//        SpecialtyEntity specialtyEntity = specialtyRepository.findByName(specialistDto.getSpecialty())
//                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found for update"));
        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specialist not found"));
        specialistEntity = specialistMapper.forUpdate(specialistDto, specialistEntity);
        specialistRepository.save(specialistEntity);
//        specialistEntity = specialistMapper.toEntityFromUpdateAndCreate(specialistDto);
//        specialistEntity.setSpecialty(specialtyEntity);
//        specialistRepository.save(specialistEntity);
        log.info("Action.log.updateSpecialist ended for specialist {}", specialistDto.getId());
    }


    @Override
    public void delete(Long id) {
        log.info("Action.log.deleteSpecialist started for {}", id);
        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found for delete"));
        specialistEntity.setStatus(Status.DELETED);
        specialistRepository.save(specialistEntity);
        SpecialistEntity specialistEntityCheck = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        if (specialistEntityCheck.getStatus() != Status.DELETED) throw new RuntimeException("Specialist not deleted");
        log.info("Action.log.deleteSpecialist ended for {}", id);
    }

    @Override
    public void changeStatus(Long id, Status status) {
        log.info("Action.log.changeSpecialistStatus started for {}", id);
        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found for change status"));
        if (specialistEntity.getStatus() == status) throw new RuntimeException("Same status");
        specialistEntity.setStatus(status);
        specialistRepository.save(specialistEntity);
        SpecialistEntity specialistEntityCheck = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        if (specialistEntityCheck.getStatus() != status) throw new RuntimeException("Specialist status not updated");
        log.info("Action.log.changeSpecialistStatus ended for {}", id);
    }

}