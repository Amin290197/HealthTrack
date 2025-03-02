package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.AppointmentEntity;
import com.example.rehabilitationandintegration.model.request.AppointmentChangeDto;
import com.example.rehabilitationandintegration.model.request.AppointmentRequest;
import com.example.rehabilitationandintegration.model.response.AppointmentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentRequest toRequestFromChange(AppointmentChangeDto appointmentChangeDto);

    AppointmentResponseDto toResponse(AppointmentEntity appointmentEntity);
}
