package com.example.rehabilitationandintegration.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class MeetingResponseDto {
    private LocalDate meetingDay;
    private LocalTime meetingTime;
}
