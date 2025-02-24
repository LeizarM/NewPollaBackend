package com.polla.bo.polla.domain.repository;


import com.polla.bo.polla.domain.model.Torneo;

import java.util.List;

public interface TorneoRepository {


    List<Torneo> getAll();

    boolean register(Torneo torneo, String acc);

}
