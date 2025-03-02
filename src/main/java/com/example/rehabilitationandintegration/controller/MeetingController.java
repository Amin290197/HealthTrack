package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.model.MeetingDto;
import com.example.rehabilitationandintegration.specification.MeetingFilter;
import com.example.rehabilitationandintegration.model.response.MeetingResponseDto;
import com.example.rehabilitationandintegration.model.request.MeetingCreateDto;
import com.example.rehabilitationandintegration.service.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;


    @GetMapping("/{id}/cancel/user/{userId}")
    public ResponseEntity<Void> cancelMeeting(@PathVariable Long userId, @PathVariable Long id) {
        meetingService.cancel(userId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/create/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MeetingResponseDto> createMeeting(@PathVariable Long userId, @RequestBody @Valid MeetingCreateDto meetingDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.create(userId, meetingDto));
    }

    @PostMapping("/{id}/changeDate/user/{userId}")
    public ResponseEntity<MeetingResponseDto> changeDate(@PathVariable Long userId, @PathVariable Long id,
                                         @RequestBody @Valid MeetingCreateDto meetingCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.changeDate(userId, id, meetingCreateDto));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<MeetingDto>> getAll(Pageable pageable, @RequestBody MeetingFilter meetingFilter){
        return ResponseEntity.status(HttpStatus.OK).body(meetingService.getAll(pageable, meetingFilter));
    }

    @GetMapping("/user/{id}")
    private ResponseEntity<Page<MeetingDto>> userMeetings(@PathVariable Long id, Pageable pageable,
                                          @RequestParam MeetingAndAppointmentStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(meetingService.userMeetings(id, pageable, status));
    }
}