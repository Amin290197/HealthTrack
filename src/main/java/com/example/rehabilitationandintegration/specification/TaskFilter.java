package com.example.rehabilitationandintegration.specification;

import com.example.rehabilitationandintegration.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;
@Data
public class TaskFilter implements Specification {

    private String name;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (Objects.nonNull(name)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (Objects.nonNull(startDateFrom)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDateFrom));
        }
        if (Objects.nonNull(startDateTo)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDateTo));
        }
        if (Objects.nonNull(endDateFrom)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDateFrom));
        }
        if (Objects.nonNull(endDateTo)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDateTo));
        }
        if (Objects.nonNull(status)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }

        return predicate;
    }
//    private PatientEntity patient;
//    private SpecialistEntity specialist;

}
