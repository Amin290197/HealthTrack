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
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MeetingFilter implements Specification {
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime created;
    @Enumerated(EnumType.STRING)
    private MeetingAndAppointmentStatus status;

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (date!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("date"), date));
        }
        if (time!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("time"), time));
        }
        if (created!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("created"), created));
        }
        if (status!= null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }

        return predicate;
    }
}
