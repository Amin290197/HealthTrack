package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.Role;
import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "roles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role name;

//    @ManyToMany(mappedBy = "roles")
//    private List<UserEntity> users;
}