package com.polla.bo.polla.infrastructure.persistence;


import com.polla.bo.polla.domain.model.Equipo;
import com.polla.bo.polla.domain.repository.EquipoRepository;
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
public class EquipoRepositoryImpl implements EquipoRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Obtiene todos los equipos
     * @return
     */
    @Override
    public List<Equipo> getAll() {
        String sql = "execute p_list_Equipo @ACCION = ?";
        Object[] params = new Object[]{"L"};
        int[] types = new int[]{Types.VARCHAR};

        try {
            return jdbcTemplate.query(
                    sql,
                    (ResultSet rs, int rowNum) -> Equipo.builder()
                            .codEquipo(rs.getInt(1))
                            .nombre(rs.getString(2))
                            .descripcion(rs.getString(3))
                            .audUsuario(rs.getInt(4))
                            .build(),
                    params
            );
        } catch (Exception e) {
            log.error("Error obteniendo todos los equipos", e);
            throw new RuntimeException("Error al obtener equipos", e);
        }
    }

    @Override
    public boolean register(Equipo equipo, String acc) {


        String sql = "execute p_abm_equipo @codEquipo=?, @nombre=?, @descripcion=?, @audUsuario=?, @rutaBanderaImgPagina=?, @ACCION=?";

        try {
            jdbcTemplate.update(sql, ps -> ParameterSetter.setParameters(ps, equipo, acc));
            return true;
        } catch (Exception e) {
            log.error("Error registering equipo: {}", equipo.getNombre(), e);
            throw new RuntimeException("Error registering equipo", e);
        }
    }
}
