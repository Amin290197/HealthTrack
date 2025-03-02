package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.ScheduleEntity;
import com.example.rehabilitationandintegration.model.ScheduleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleDto toDto(ScheduleEntity scheduleEntity);

    ScheduleEntity toEntity(ScheduleDto scheduleDto);
}
