package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.*;
import com.example.rehabilitationandintegration.dao.repository.AccountRepository;
import com.example.rehabilitationandintegration.dao.repository.PaymentRepository;
import com.example.rehabilitationandintegration.dao.repository.PriceRepository;
import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.model.request.AccountRequestDto;
import com.example.rehabilitationandintegration.model.request.AppointmentRequest;
import com.example.rehabilitationandintegration.model.request.AppointmentRequestList;
import com.example.rehabilitationandintegration.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PriceRepository priceRepository;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Double calculateTotal(UserEntity user, AppointmentRequestList appointmentRequestList) {
        log.info("Action.log.processPayment started for user {}", user.getId());
        Double sum = Double.valueOf(0);

        int week = 1;

        for (int i = 0; i < appointmentRequestList.getAppointmentRequests().size(); i++) {
            AppointmentRequest appointmentRequest = appointmentRequestList.getAppointmentRequests().get(i);
            PriceEntity price = priceRepository.findByDuration(appointmentRequest.getDuration())
                    .orElseThrow(() -> new ResourceNotFoundException("Duration not found"));
            sum += price.getPrice();

            if (week == appointmentRequestList.getWeek() &&
                    appointmentRequest.equals(appointmentRequestList.getAppointmentRequests().getLast())) {
                break;
            }
            if (i == appointmentRequestList.getAppointmentRequests().size() - 1) {
                week++;
                i = -1;
            }
        }
        payment(appointmentRequestList.getAccountRequestDto(), sum, user);
        log.info("Action.log.processPayment ended for user {}", user.getId());
        return sum;
    }

    @Transactional
    @Override
    public void payment(AccountRequestDto accountRequestDto, Double sum, UserEntity user) {
        log.info("Action.log.payment started user {}", user.getId());

        AccountEntity account = accountRepository
                .findByCardNumberAndCvvAndExpiryDate(accountRequestDto.getCardNumber(),
                        accountRequestDto.getCvv(), accountRequestDto.getExpiryDate())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (account.getBalance() >= sum) {
            if (account.getCvv().equals(accountRequestDto.getCvv())
                    && account.getExpiryDate().equals(accountRequestDto.getExpiryDate())
                    && account.getExpiryDate().isAfter(LocalDate.now())) {
                account.setBalance(account.getBalance() - sum);
                accountRepository.save(account);
            } else {
                throw new RuntimeException("Invalid CVV or expiry date");
            }
        } else {
            throw new RuntimeException("Insufficient funds");
        }
        paymentRecord(user, account, sum);
        log.info("Action.log.payment ended user {}", user.getId());
    }

    @Override
    public void paymentRecord(UserEntity user, AccountEntity account, Double sum) {
        log.info("Action.log.paymentRecord started for user {}", user.getId());
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .user(user)
                .senderCard(account.getCardNumber())
                .sum(sum)
                .date(LocalDate.now())
                .build();

        paymentRepository.save(paymentEntity);
        log.info("Action.log.paymentRecord ended for user {}", user.getId());
    }


}
