package com.polla.bo.polla.domain.repository;

import com.polla.bo.polla.domain.model.EquipoTorneo;
import com.polla.bo.polla.domain.model.Torneo;

import java.util.List;

public interface EquipoTorneoRepository {


    List<EquipoTorneo> getEquiposXTorneo();

    boolean register(EquipoTorneo mb, String acc);

}
