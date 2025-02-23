package com.polla.bo.polla.domain.repository;

import com.polla.bo.polla.domain.model.Usuario;

import java.util.Optional;

public interface UserRepository {

    Optional<Usuario> findByUsername(String username);
    boolean register(Usuario user, String acc);

}
