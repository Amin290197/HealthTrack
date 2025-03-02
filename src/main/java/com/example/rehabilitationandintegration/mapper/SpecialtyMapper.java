package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.SpecialtyEntity;
import com.example.rehabilitationandintegration.model.SpecialtyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {
    SpecialistEntity toEntity(SpecialtyDto specialtyDto);

    SpecialtyDto toDto(SpecialtyEntity specialtyEntity);
}
