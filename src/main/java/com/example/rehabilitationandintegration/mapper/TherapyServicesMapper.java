package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.TherapyServicesEntity;
import com.example.rehabilitationandintegration.model.TherapyServicesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TherapyServicesMapper {
    TherapyServicesEntity toEntity(TherapyServicesDto therapyServicesDto);

    TherapyServicesDto toDto(TherapyServicesEntity therapyServicesEntity);


//    @Mapping(target = "therapy", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    TherapyServicesEntity update(TherapyServicesDto servicesDto, TherapyServicesEntity servicesEntity);
}
