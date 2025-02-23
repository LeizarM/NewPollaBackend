package com.polla.bo.polla.infrastructure.persistence;

import com.polla.bo.polla.domain.model.Usuario;
import com.polla.bo.polla.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;


    private final RowMapper<Usuario> userRowMapper = (rs, rowNum) ->
            Usuario.builder()
                    .codUsuario(rs.getInt("codUsuario"))
                    .codParticipante(rs.getInt("codParticipante"))
                    .usuario(rs.getString("usuario"))
                    .contrasena(rs.getString("contrasena"))
                    .estado(rs.getString("estado"))
                    .esAdmin(rs.getInt("esAdm"))
                    .audUsuario(rs.getString("audUsuario"))
                    .build();

    @Override
    public Optional<Usuario> findByUsername(String username) {
        String sql = "exec p_list_usuario @usuario = ?, @ACCION = ? ";
        log.debug("Executing stored procedure: {} with username: {}", sql, username);

        try {
            return jdbcTemplate.query(
                    sql,
                    ps -> {
                        ps.setString(1, username);  // @usuario
                        ps.setString(2, "C");  // @ACCION - asumiendo que "BUSCAR" es la acción para búsqueda
                    },
                    userRowMapper
            ).stream().findFirst();
        } catch (Exception e) {
            log.error("Error finding user by username: {}", username, e);
            throw e;
        }
    }

    @Override
    public boolean register(Usuario user, String acc) {
        String sql = "{call p_abm_usuario (?, ?, ?, ?, ?, ?, ?, ?)}";  // Sintaxis para stored procedure

        try {
            jdbcTemplate.update(
                    sql,
                    ps -> {
                        ps.setInt(1, user.getCodUsuario());        // @codUsuario
                        ps.setInt(2, user.getCodParticipante());   // @codParticipante
                        ps.setString(3, user.getUsuario());           // @usuario
                        ps.setString(4, user.getContrasena());        // @contrasena
                        ps.setString(5, user.getEstado());            // @estado
                        ps.setInt(6, user.getEsAdmin());          // @esAdmin
                        ps.setString(7, user.getAudUsuario());        // @audUsuario
                        ps.setString(8, acc);                         // @ACCION
                    }
            );

            // Verifica si el usuario fue registrado correctamente
            return findByUsername(user.getUsuario())
                    .map(usuario -> true)
                    .orElseThrow(() -> new RuntimeException("No se pudo registrar el usuario"));
        } catch (Exception e) {
            log.error("Error registering user: {}", user.getUsuario(), e);
            throw e;
        }
    }

}
