package com.polla.bo.polla.domain.repository;

import com.polla.bo.polla.domain.model.Equipo;

import java.util.List;

public interface EquipoRepository {


    List<Equipo> getAll();

    boolean register(Equipo equipo, String acc);
}
