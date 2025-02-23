package com.polla.bo.polla.domain.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipo  {
    private int codEquipo;
    private String nombre;
    private String descripcion;
    private int audUsuario;
    private String rutaBanderaImgPagina;
}