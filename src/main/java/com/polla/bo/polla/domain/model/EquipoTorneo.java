package com.polla.bo.polla.domain.model;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EquipoTorneo {
    private String codEqParticipante;
    private int codTorneo;
    private int codEquipo;
    private int enCuartos;
    private int ocupoLugar;
    private int audUsuario;

}