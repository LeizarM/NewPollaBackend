package com.polla.bo.polla.infrastructure.persistence;

import com.polla.bo.polla.domain.model.EquipoTorneo;
import com.polla.bo.polla.domain.repository.EquipoTorneoRepository;
import com.polla.bo.polla.infrastructure.utils.ParameterSetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class EquipoTorneoRepositoryImpl implements EquipoTorneoRepository {


    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<EquipoTorneo> getEquiposXTorneo() {
        return Collections.emptyList();
    }


    @Override
    public boolean register(EquipoTorneo mb, String acc) {

        String sql = "execute p_abm_EquipoTorneo @codEqParticipante=?, @codTorneo=?, @codEquipo=?, @enCuartos=?, @ocupoLugar=? ,@audUsuario=?, @ACCION=?";

        try {
            jdbcTemplate.update(sql, ps -> ParameterSetter.setParameters(ps, mb, acc));
            return true;
        } catch (Exception e) {
            log.error("Error registering equipo Torneo", e);
            throw new RuntimeException("Error registering equipo", e);
        }

    }
}
