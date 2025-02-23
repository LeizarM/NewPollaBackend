package com.polla.bo.polla.application.dto;

import lombok.Builder;
import lombok.Data;

public class EquipoDto {

    @Data
    @Builder
    public static class EquipoResponse {
        private int codEquipo;
        private String nombre;
        private String descripcion;
        private int audUsuario;



    }
}
