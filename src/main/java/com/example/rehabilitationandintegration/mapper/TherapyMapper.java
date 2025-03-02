package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.TherapyEntity;
import com.example.rehabilitationandintegration.model.response.TherapyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TherapyMapper {

    TherapyDto toDto(TherapyEntity therapyEntity);

    TherapyEntity toEntity(TherapyDto createDto);
}
