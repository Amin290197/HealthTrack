package com.example.rehabilitationandintegration.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE(1),
    INACTIVE(0),
    DELETED(-1);

    final Integer status;

    Status(Integer status){
        this.status = status;
    }
}
