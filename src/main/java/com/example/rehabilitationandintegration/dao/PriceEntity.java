package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.PriceCurrency;
import com.example.rehabilitationandintegration.enums.PriceMinute;
import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prices")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    @Enumerated(EnumType.STRING)
    private PriceCurrency currency;
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private PriceMinute minute;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany(mappedBy = "prices")
    private List<TherapyEntity> therapy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceEntity that = (PriceEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getDuration(), that.getDuration()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getDuration(), getStatus());
    }
}
