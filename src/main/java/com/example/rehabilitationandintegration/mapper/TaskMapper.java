package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.TaskEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.enums.SpecialtyType;
import com.example.rehabilitationandintegration.model.TaskDto;
import com.example.rehabilitationandintegration.model.request.TaskCreateDto;
import com.example.rehabilitationandintegration.model.request.TaskUpdateDto;
import com.example.rehabilitationandintegration.model.request.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskEntity toEntity(TaskCreateDto taskCreateDto);

    TaskEntity toEntity(TaskUpdateDto taskUpdateDto);

    @Mapping(target = "id", ignore = true)
    TaskEntity forUpdate(TaskDto taskDto, @MappingTarget TaskEntity taskEntity);

    @Mapping(target = "specialistName",source = "specialist", qualifiedByName = "getFullName")
    @Mapping(target = "therapy",source = "specialist", qualifiedByName = "getSpecialty")
    TaskDto toDto(TaskEntity taskEntity);

    @Named("getFullName")
    default String getFullName(SpecialistEntity specialist){
        if (specialist == null) {
            return "";
        }
       return specialist.getName()+" "+specialist.getSurname();
    }
    @Named("getSpecialty")
    default String getSpecialty(SpecialistEntity specialist){
        if (specialist == null) {
            return "";
        }
        return specialist.getSpecialty().getName().toString();
    }

    TaskEntity toEntity(TaskDto taskDto);
}
