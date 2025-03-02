package com.example.rehabilitationandintegration.model.response;

import com.example.rehabilitationandintegration.enums.DayOfWeekEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class FreeScheduleResponse {
    private DayOfWeekEnum day;
    private List<LocalTime[]> intervals;

    public FreeScheduleResponse(DayOfWeekEnum day, List<LocalTime[]> intervals) {
        this.day = day;
        this.intervals = intervals;
    }
//    private DayOfWeekEnum day;
//    private LocalTime startTime;
//    private LocalTime endTime;
//
//    public FreeScheduleResponse(DayOfWeekEnum day, LocalTime startTime, LocalTime endTime) {
//        this.day = day;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
}
