package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.MeetingEntity;
import com.example.rehabilitationandintegration.model.MeetingDto;
import com.example.rehabilitationandintegration.model.request.MeetingCreateDto;
import com.example.rehabilitationandintegration.model.response.MeetingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface MeetingMapper {

    MeetingEntity toEntityFromCreate(MeetingCreateDto meetingCreateDto);

    MeetingDto toDto(MeetingEntity meetingEntity);

    MeetingEntity toEntityForCreate(MeetingCreateDto meetingCreateDto);

//    @Mapping(source = "meetingTime", target = "meetingDay", qualifiedByName = "getMeetingDay")
//    @Mapping(source = "meetingTime", target = "meetingTime", qualifiedByName = "getMeetingTime")
//    MeetingResponseDto toResponseDtoFromEntity(MeetingEntity meetingEntity);

    @Named("getMeetingDay")
    default LocalDate getMeetingDate(LocalDateTime meetingTime){
        return meetingTime.toLocalDate();
    }

    @Named("getMeetingTime")
    default LocalTime getMeetingTime(LocalDateTime meetingTime){
        return meetingTime.toLocalTime();
    }
}
