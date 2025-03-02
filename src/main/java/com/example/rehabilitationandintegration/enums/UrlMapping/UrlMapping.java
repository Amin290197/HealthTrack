package com.example.rehabilitationandintegration.enums.UrlMapping;

import com.example.rehabilitationandintegration.enums.Role;
import lombok.Getter;

@Getter
public enum UrlMapping {
    PATIENT(Role.PATIENT.name(), new String[]{

            "/user/{id}/update",
            "/user/{id}/delete",
            "/user/{id}",
            "/specialty/all",
            "/task/{id}",
            "/task/patient/{id}",
            "/specialist/all",
            "/services/all",
            "/schedule/specialist/{id}",
            "/schedule/specialist/{id}/freeTime",
            "/price/{id}",
            "/price/therapy/{id}",
            "/meeting/{id}/changeDate/user/{userId}",
            "/meeting/create/user/{userId}",
            "/meeting/{id}/cancel/user/{userId}",
            "/meeting/user/{id}",
            "/certificate/specialist/{id}",
            "/appointment/{id}/cancel/user/{userId}",
            "/appointment/{id}",
            "/appointment/user/{id}",
            "/appointment/register/user/{userId}/specialist{id}",
            "/appointment/{id}/change/user/{userId}",

    }),
    ADMIN(Role.ADMIN.name(), new String[]{
            "/user/{id}/changeStatus",
            "/user/all",
            "/user/{id}/activate",
            "/user/{id}/delete",

            "/therapy/create",
            "/therapy/{id}/update",
            "/therapy/{id}/changeStatus",
            "/therapy/{id}/delete",

            "/task/create/patient/{patientId}/specialist/{specialistId}",
            "/task/{id}/update",
            "/task/{id}",
            "/task/specialist/{id}",
            "/task/all",

            "/specialist/{id}/changeStatus",
            "/specialist/create",
            "/specialist/{id}/update",
            "/specialist/{id}/allPatients",
            "/specialist/{id}/delete",

            "/services/create/{therapyId}",
            "/services/{id}/update",
            "/services/{id}/changeStatus",
            "/services/all",

            "/schedule/create/specialist/{id}",
            "/schedule/{id}/update",
            "/schedule/delete/{id}",

            "/price/create",
            "/price/{id}/update",
            "/price/{id}/changeStatus",
            "/price/all",

            "/meeting/all",

            "/certificate/create/specialist/{id}",
            "/certificate/{id}/update",
            "/certificate/all",
            "/certificate/{id}/delete",

            "/appointment/all",
            "/appointment/specialist/{id}",

            "/specialty/{id}",

    }),
    PERMIT_ALL(null, new String[]{
            "/api/authenticate",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/error",

            "/auth/sign-up",
            "/auth/sign-in",
            "/auth/forgotPassword",
            "/auth/recoveryPassword",

            "/specialist/specialty/{id}",
            "/specialist/{id}",
            "/services/{therapyId}",
            "/therapy/all",
            "/therapy/{id}",
            "/specialty/all",
            "/certificate/specialist/{id}",

    }),
    ANY_AUTHENTICATED(null, new String[]{
            "/auth/refreshToken",
            "/user/changePassword"
    });


    private final String role;
    private final String[] urls;

    UrlMapping(String role, String[] urls) {
        this.role = role;
        this.urls = urls;
    }


}
