package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.*;
import com.example.rehabilitationandintegration.dao.repository.*;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
//import com.example.rehabilitationandintegration.mapper.SpecialtyMapper;
import com.example.rehabilitationandintegration.mapper.TherapyMapper;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import com.example.rehabilitationandintegration.model.response.TherapyDto;
import com.example.rehabilitationandintegration.service.TherapyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TherapyServiceImpl implements TherapyService {
    private final TherapyRepository therapyRepository;
    private final TherapyMapper therapyMapper;
    private final ServicesRepository servicesRepository;


    @Override
    public Page<TherapyDto> getAll(Pageable pageable, Status status) {
        log.info("Action.log.getAll started");

        Page<TherapyEntity> therapyEntities;
        if (status == null) {
            therapyEntities = therapyRepository.findAll(pageable);
        }
        else {
            therapyEntities = therapyRepository.findAllByStatus(pageable, status);
        }
        if (therapyEntities.isEmpty()) throw new ResourceNotFoundException("Not found therapies");
        List<TherapyDto> therapyDtoList = therapyEntities.stream()
                .map(therapyMapper::toDto).toList();
        PageImpl<TherapyDto> therapyDtoPage =
                new PageImpl<>(therapyDtoList, pageable, therapyEntities.getTotalElements());
        log.info("Action.log.getAll ended");

        return therapyDtoPage;
    }

    @Override
    public TherapyDto get(Long id) {
        log.info("Action.log.get started for Therapy {}", id);
        TherapyEntity therapyEntity = therapyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Therapy not found"));
        TherapyDto therapyDto = therapyMapper.toDto(therapyEntity);
        log.info("Action.log.get ended for Therapy {}", id);
        return therapyDto;
    }

    @Override
    public void create(TherapyDto therapyDto) {
        log.info("Action.log.create started for Therapy");
        TherapyEntity therapyEntity = therapyMapper.toEntity(therapyDto);
        therapyEntity.setStatus(Status.ACTIVE);
        TherapyEntity checkTherapy = therapyRepository.save(therapyEntity);
        if (checkTherapy.getId() == null) throw new RuntimeException("Therapy not create");
        log.info("Action.log.create ended for Therapy {}", checkTherapy.getId());
    }

    @Override
    public void update(TherapyDto therapyCreateDto, Long id) {
        log.info("Action.log.update started for Therapy {}", id);
        TherapyEntity therapyEntity = therapyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Therapy not found for update"));
        therapyEntity.setName(therapyCreateDto.getName());
        therapyEntity = therapyRepository.save(therapyEntity);
        if (therapyEntity.getId() == null) throw new RuntimeException("Therapy not update");
        log.info("Action.log.update ended for Therapy {}", id);
    }

    @Override
    public void delete(Long id) {
        log.info("Action.log.delete started for Therapy {}", id);

        TherapyEntity therapyEntity = therapyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Therapy not found for delete"));
        if (therapyEntity.getStatus() != Status.DELETED){
        therapyEntity.setStatus(Status.DELETED);
        }
        else throw new RuntimeException("Therapy already deleted");
        TherapyEntity checkTherapyStatus = therapyRepository.save(therapyEntity);

        if (checkTherapyStatus.getStatus() != Status.DELETED) throw new RuntimeException("Therapy not deleted");
        log.info("Action.log.delete ended for Therapy {}", id);
    }

    @Override
    public void changeStatus(Long id, ActiveInactiveDto status) {
        log.info("Action.log.changeStatus started for {}", id);
        TherapyEntity therapyEntity = therapyRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Therapy not found"));
        if (therapyEntity.getStatus() == status.getStatus())
            throw new RuntimeException("Therapy status is already ");
        therapyEntity.setStatus(status.getStatus());
        therapyRepository.save(therapyEntity);
        log.info("Action.log.changeStatus ended for {}", id);

    }

}
