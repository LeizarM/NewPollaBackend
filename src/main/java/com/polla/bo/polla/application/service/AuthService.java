package com.polla.bo.polla.application.service;
import com.polla.bo.polla.domain.exception.AuthenticationException;
import com.polla.bo.polla.domain.model.Usuario;
import com.polla.bo.polla.domain.repository.UserRepository;
import com.polla.bo.polla.infrastructure.config.JwtConfig;
import com.polla.bo.polla.application.dto.LoginDtos.LoginResponse;
import com.polla.bo.polla.application.dto.LoginDtos.LoginRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Usuario user = userRepository.findByUsername(request.getUsuario())
                .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getContrasena(), user.getContrasena())) {
            throw new AuthenticationException("Contraseña incorrecta");
        }



        String token = jwtConfig.generateToken(user.getUsuario(), user.getEsAdmin());

        return LoginResponse.builder()
                .token(token)
                .usuario(user.getUsuario())
                .esAdmin(user.getEsAdmin())
                .build();
    }

}
