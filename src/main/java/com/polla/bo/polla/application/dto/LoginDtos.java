package com.polla.bo.polla.application.dto;

import lombok.Builder;
import lombok.Data;

public class LoginDtos {

    @Data
    public static class LoginRequest {
        private String usuario;
        private String contrasena;
    }

    @Data
    @Builder
    public static class LoginResponse {
        private int codUsuario;
        private String token;
        private String usuario;
        private int esAdmin;
    }

}
