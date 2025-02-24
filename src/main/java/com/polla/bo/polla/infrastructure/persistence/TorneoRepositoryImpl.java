package com.polla.bo.polla.infrastructure.persistence;

import com.polla.bo.polla.domain.model.Equipo;
import com.polla.bo.polla.domain.model.Torneo;
import com.polla.bo.polla.domain.repository.TorneoRepository;
import com.polla.bo.polla.infrastructure.utils.ParameterSetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TorneoRepositoryImpl implements TorneoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Torneo> getAll() {
        String sql = "execute p_list_Torneo @ACCION = ?";
        Object[] params = new Object[]{"L"};
        int[] types = new int[]{Types.VARCHAR};

        try {
            return jdbcTemplate.query(
                    sql,
                    (ResultSet rs, int rowNum) -> Torneo.builder()
                            .codTorneo(rs.getInt(1))
                            .nombre(rs.getString(2))
                            .fechaInicio(rs.getDate(3))
                            .fechaFin(rs.getDate(4))
                            .montoTotal(rs.getFloat(5))
                            .montoFecha(rs.getFloat(6))
                            .montoPolla(rs.getFloat(7))
                            .finalizado(rs.getString(8))
                            .audUsuario(rs.getInt(9))
                            .build(),
                    params
            );
        } catch (Exception e) {
            log.error("Error obteniendo todos los equipos", e);
            throw new RuntimeException("Error al obtener equipos", e);
        }
    }

    @Override
    public boolean register(Torneo torneo, String acc) {


        String sql = "execute p_abm_Torneo @codTorneo=?, @nombre=?, @fechaInicio=?, @fechaFin=?, @montoTotal=?, @montoFecha=?, @montoPolla=?, @finalizado=? ,@audUsuario=?, @ACCION=?";

        try {
            jdbcTemplate.update(sql, ps -> ParameterSetter.setParameters(ps, torneo, acc));
            return true;
        }catch ( Exception e){

            log.error("Error registering Torneo: {}", torneo.getNombre(), e);
            throw new RuntimeException("Error registering torneo", e);

        }


    }
}
