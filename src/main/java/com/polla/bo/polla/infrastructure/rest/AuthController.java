package com.polla.bo.polla.infrastructure.rest;

import com.polla.bo.polla.application.dto.LoginDtos.LoginRequest;
import com.polla.bo.polla.application.dto.LoginDtos.LoginResponse;
import com.polla.bo.polla.application.service.AuthService;
import com.polla.bo.polla.domain.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("Received login request for user: {}", request.getUsuario());

        try {
            LoginResponse response = authService.login(request);
            log.info("Login successful for user: {}", request.getUsuario());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", request.getUsuario(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during login for user: {}", request.getUsuario(), e);
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }
}