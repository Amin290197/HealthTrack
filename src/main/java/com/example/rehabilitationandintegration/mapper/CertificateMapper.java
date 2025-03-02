package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.CertificateEntity;
import com.example.rehabilitationandintegration.model.CertificateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateDto toDto(CertificateEntity certificateEntity);

    CertificateEntity toEntity(CertificateDto certificateDto);
}
