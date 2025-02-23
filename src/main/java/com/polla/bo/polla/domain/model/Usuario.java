package com.polla.bo.polla.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private int codUsuario;
    private int codParticipante;
    private String usuario;
    private String contrasena;
    private String estado;
    private int esAdmin;
    private String audUsuario;
}
