package com.example.rehabilitationandintegration.specification;

import com.example.rehabilitationandintegration.enums.Gender;
import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserFilter implements Specification {
    private final Status status;
    private final String name;
    private final String surname;
    private final LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private final Gender gender;
    @Enumerated(EnumType.STRING)
    private final Language language;
    private final LocalDateTime registrationDate;
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (status != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }
        if (name != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (surname != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + surname.toLowerCase() + "%"));
        }
        if (birthDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("birthDate"), birthDate));
        }
        if (gender != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gender"), gender));
        }
//        if (phoneNumber != null) {
//            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
//        }
        if (language != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("language"), language));
        }
        if (registrationDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("registrationDate"), registrationDate));
        }

        return predicate;
    }
}
