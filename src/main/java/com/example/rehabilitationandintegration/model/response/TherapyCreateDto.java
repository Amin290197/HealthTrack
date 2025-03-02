package com.example.rehabilitationandintegration.model.response;

import com.example.rehabilitationandintegration.dao.TherapyServicesEntity;
import lombok.Data;

import java.util.List;

@Data
public class TherapyCreateDto {
    private String name;

    private List<TherapyServicesEntity> services;

}
