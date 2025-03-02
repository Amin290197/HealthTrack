package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.PriceEntity;
import com.example.rehabilitationandintegration.model.PriceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceEntity toEntity(PriceDto priceDto);

    PriceDto toDto(PriceEntity priceEntity);
}
