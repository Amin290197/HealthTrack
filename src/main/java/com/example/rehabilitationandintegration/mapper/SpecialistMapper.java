package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.SpecialistDto;
import com.example.rehabilitationandintegration.model.request.SpecialistCreateUpdateDto;
import com.example.rehabilitationandintegration.model.request.SpecialistUpdateDto;
import com.example.rehabilitationandintegration.model.request.UserUpdateDto;
import com.example.rehabilitationandintegration.model.response.SpecialistAllResponseDto;
import com.example.rehabilitationandintegration.model.response.SpecialistResponseDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface SpecialistMapper {

    SpecialistResponseDto toResponseDto(SpecialistEntity specialistEntity);

    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    SpecialistEntity toEntityFromUpdateAndCreate(SpecialistCreateUpdateDto specialistCreateUpdateDto);

    @Mapping(target = "id", ignore = true)
    SpecialistEntity forUpdate(SpecialistCreateUpdateDto specialistCreateUpdateDto,
                               @MappingTarget SpecialistEntity specialistEntity);

    @AfterMapping
    default void setDefaultValues(SpecialistCreateUpdateDto specialistCreateUpdateDto, @MappingTarget SpecialistEntity specialistEntity) {
        if (specialistEntity.getRegistrationDate() == null) {
            specialistEntity.setRegistrationDate(LocalDateTime.now());
        }
        if (specialistEntity.getStatus() == null) {
            specialistEntity.setStatus(Status.ACTIVE);
        }
    }


    SpecialistEntity toEntity(SpecialistDto specialistDto);

    SpecialistAllResponseDto toAllDto(SpecialistEntity specialistEntity);

    SpecialistDto toDto(SpecialistEntity specialistEntity);



}
