package com.polla.bo.polla.domain.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Torneo {

    private int codTorneo;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private float montoTotal;
    private float montoFecha;
    private float montoPolla;
    private String finalizado;
    private int audUsuario;

}