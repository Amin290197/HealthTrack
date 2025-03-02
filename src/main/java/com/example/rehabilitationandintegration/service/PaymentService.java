package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.dao.AccountEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.model.request.AccountRequestDto;
import com.example.rehabilitationandintegration.model.request.AppointmentRequestList;

public interface PaymentService {
    Double calculateTotal(UserEntity user, AppointmentRequestList appointmentRequestList);

    void payment(AccountRequestDto accountRequestDto, Double sum, UserEntity user);

    void paymentRecord(UserEntity user, AccountEntity account, Double sum);
}
