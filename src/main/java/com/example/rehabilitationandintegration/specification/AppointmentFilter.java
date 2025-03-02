package com.example.rehabilitationandintegration.specification;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentFilter implements Specification {
    private LocalDate dayFrom;
    private LocalDate dayTo;
    private LocalTime startTimeFrom;
    private LocalTime startTimeTo;
    private Integer durationFrom;
    private Integer durationTo;
    @Enumerated(EnumType.STRING)
    private MeetingAndAppointmentStatus status;

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (dayFrom!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("day"), dayFrom));
        }
        if (dayTo!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("day"), dayTo));
        }
        if (startTimeFrom!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTimeFrom));
        }
        if (startTimeTo!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), startTimeTo));
        }
        if (durationFrom!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), durationFrom));
        }
        if (durationTo!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("duration"), durationTo));
        }
        if (status!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }


        return predicate;
    }
}
