package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.TherapyEntity;
import com.example.rehabilitationandintegration.dao.TherapyServicesEntity;
import com.example.rehabilitationandintegration.dao.repository.ServicesRepository;
import com.example.rehabilitationandintegration.dao.repository.TherapyRepository;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.TherapyServicesMapper;
import com.example.rehabilitationandintegration.model.TherapyServicesDto;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import com.example.rehabilitationandintegration.service.TherapyServicesService;
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
public class TherapyServicesServiceImpl implements TherapyServicesService {
    private final TherapyRepository therapyRepository;
    private final ServicesRepository servicesRepository;
    private final TherapyServicesMapper therapyServicesMapper;

    @Override
    public Page<TherapyServicesDto> allByTherapy(Pageable pageable, Long id) {
        log.info("Action.log.allByTherapy started for therapy {}", id);
        TherapyEntity therapy = therapyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Therapy not found"));
        Page<TherapyServicesEntity> therapyServicesPage = servicesRepository.findAllByTherapy(pageable, therapy);
        List<TherapyServicesDto> therapyServicesDtoList = therapyServicesPage.stream().map(therapyServicesMapper::toDto).toList();
        PageImpl<TherapyServicesDto> servicesDtoPage = new PageImpl<>(therapyServicesDtoList, pageable,
                therapyServicesPage.getTotalElements());
        log.info("Action.log.allByTherapy ended for therapy {}", id);
        return servicesDtoPage;
    }

    @Override
    public Page<TherapyServicesDto> all(Pageable pageable, Status status) {
        log.info("Action.log.all started");
        Page<TherapyServicesEntity> therapyServicesEntityPage ;
        if (status == null){
            therapyServicesEntityPage = servicesRepository.findAll(pageable);
        }
        else {
            therapyServicesEntityPage = servicesRepository.findAllByStatus(pageable, status);
        }
        if (therapyServicesEntityPage.isEmpty()) throw new ResourceNotFoundException("Not found any service");
        List<TherapyServicesDto> therapyServicesDtoList = therapyServicesEntityPage.stream()
                .map(therapyServicesMapper::toDto).toList();
        PageImpl<TherapyServicesDto> servicesDtoPage = new PageImpl<>(therapyServicesDtoList,
                pageable, therapyServicesEntityPage.getTotalElements());
        log.info("Action.log.all ended");
        return servicesDtoPage;
    }

    @Override
    public void create(Long id, TherapyServicesDto therapyServicesDto) {
        log.info("Action.create service started for therapy {}", id);
        TherapyEntity therapy = therapyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Therapy not found"));
        TherapyServicesEntity therapyServicesEntity = therapyServicesMapper.toEntity(therapyServicesDto);
        therapyServicesEntity.setStatus(Status.ACTIVE);
        if (therapy.getServices().isEmpty()){
            List<TherapyServicesEntity> services = new ArrayList<>();
            services.add(therapyServicesEntity);
            therapy.setServices(services);
            therapyRepository.save(therapy);
        }
        therapy.getServices().add(therapyServicesEntity);
        therapyRepository.save(therapy);
        log.info("Action.create service ended for therapy {}", id);
    }

    @Override
    public void update(Long id, TherapyServicesDto therapyServicesDto) {
        log.info("Action.log.update started for service {}", id);
        TherapyServicesEntity service = servicesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        service.setName(therapyServicesMapper.toEntity(therapyServicesDto).getName());
        servicesRepository.save(service);
        log.info("Action.log.update ended for service {}", id);
    }

    @Override
    public void delete(Long id) {
        log.info("Action.log.delete started for service {}", id);
        TherapyServicesEntity service = servicesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        if (service.getStatus() != Status.DELETED){
            service.setStatus(Status.DELETED);
            servicesRepository.save(service);
        }
        log.info("Action.log.delete ended for service {}", id);
    }

    @Override
    public void changeStatus(Long id, ActiveInactiveDto activeInactiveDto) {
        log.info("Action.log.changeStatus started for service {}", id);
        TherapyServicesEntity servicesEntity = servicesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        if (servicesEntity.getStatus() != activeInactiveDto.getStatus()){
            servicesEntity.setStatus(activeInactiveDto.getStatus());
            servicesRepository.save(servicesEntity);
        }
        else throw new RuntimeException("Service already has this status");
        log.info("Action.log.changeStatus ended for service {}", id);
    }
}
