package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.PriceEntity;
import com.example.rehabilitationandintegration.dao.TherapyEntity;
import com.example.rehabilitationandintegration.dao.repository.PriceRepository;
import com.example.rehabilitationandintegration.dao.repository.TherapyRepository;
import com.example.rehabilitationandintegration.enums.PriceCurrency;
import com.example.rehabilitationandintegration.enums.PriceMinute;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.AlreadyException;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.PriceMapper;
import com.example.rehabilitationandintegration.model.PriceDto;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import com.example.rehabilitationandintegration.service.PriceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final TherapyRepository therapyRepository;


    @Override
    public Page<PriceDto> all(Pageable pageable, Status status) {
        log.info("Action.log.all started");
        Page<PriceEntity> priceEntityPage;
        if (status==null){
            priceEntityPage = priceRepository.findAll(pageable);
        }
        else {
            priceEntityPage = priceRepository.findAllByStatus(pageable, status);
        }
        if (priceEntityPage.isEmpty()) throw new ResourceNotFoundException("Not found prices");
        List<PriceDto> priceDtoList = priceEntityPage.stream().map(priceMapper::toDto).toList();
        PageImpl<PriceDto> priceDtoPage = new PageImpl<>(priceDtoList, pageable, priceEntityPage.getTotalElements());
        log.info("Action.log.all ended");
        return priceDtoPage;
    }

    @Override
    public PriceDto get(Long id) {
        log.info("Action.log.get started for price {}", id);
        PriceEntity priceEntity = priceRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Price not found"));
        PriceDto priceDto = priceMapper.toDto(priceEntity);
        log.info("Action.log.get ended for price {}", id);
        return priceDto;
    }

    @Override
    public void create(PriceDto priceDto) {
        log.info("Action.log.create started for therapy {}", priceDto.getPrice());
        PriceEntity priceEntity = priceMapper.toEntity(priceDto);
        priceEntity.setStatus(Status.ACTIVE);
        priceEntity.setMinute(PriceMinute.MINUTE);
        priceEntity.setCurrency(PriceCurrency.AZN);
        PriceEntity priceCheck = priceRepository.save(priceEntity);
        if (priceCheck.getId() == null) throw new RuntimeException("Price not create");
        log.info("Action.log.create ended for therapy {}", priceCheck.getId());
    }

    @Override
    public void update(Long id, PriceDto priceDto) {
        log.info("Action.log.update started for therapy {}", id);
        PriceEntity priceUpdate = priceMapper.toEntity(priceDto);
        PriceEntity priceEntity = priceRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Price with id :" + id + " not found for update"));
        priceEntity.setPrice(priceUpdate.getPrice());
        priceEntity.setDuration(priceUpdate.getDuration());
        priceRepository.save(priceEntity);
        log.info("Action.log.update ended for therapy {}", id);
    }

//    @Override
//    public void delete(Long id) {
//        log.info("Action.log.delete started for price {}", id);
//        PriceEntity priceEntity = priceRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("Price not found"));
//        if (priceEntity.getStatus() != Status.DELETED){
//            priceEntity.setStatus(Status.DELETED);
//            priceRepository.save(priceEntity);
//        }
//        log.info("Action.log.delete ended for price {}", id);
//    }

    @Override
    public void changeStatus(Long id, ActiveInactiveDto status) {
        log.info("Action.log.changeStatus started for price {}", id);
        PriceEntity priceEntity = priceRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Price not found"));
        if (priceEntity.getStatus() != status.getStatus()){
            priceEntity.setStatus(status.getStatus());
            priceRepository.save(priceEntity);
        }
        else throw new AlreadyException("Price already has this status");
        log.info("Action.log.changeStatus ended for price {}", id);
    }

    @Override
    public Page<PriceDto> byTherapy(Long id, Pageable pageable) {
        log.info("Action.log.priceTherapy started for therapy {}", id);
        TherapyEntity therapyEntity = therapyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Therapy not found"));
        Page<PriceEntity> priceEntityPage = priceRepository.findAllByTherapyAndStatus(pageable, therapyEntity, Status.ACTIVE);
        List<PriceDto> priceDtoList = priceEntityPage.stream().map(priceMapper::toDto).toList();
        PageImpl<PriceDto> priceDtoPage = new PageImpl<>(priceDtoList, pageable, priceEntityPage.getTotalElements());
        log.info("Action.log.priceTherapy ended for therapy {}", id);
        return priceDtoPage;
    }
}
