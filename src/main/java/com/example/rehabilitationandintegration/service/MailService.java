package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.model.request.AppointmentRequestList;
import com.example.rehabilitationandintegration.model.request.EmailDto;

public interface MailService {
    void recoveryPassword(EmailDto emailDto);

    void sendActiveEmail(UserEntity user);

    void registerRequestEmail(UserEntity user);

    void appointmentNotify(UserEntity user, Double sum);

}
