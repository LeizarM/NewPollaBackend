package com.polla.bo.polla.infrastructure.rest;

import com.polla.bo.polla.domain.model.Equipo;
import com.polla.bo.polla.infrastructure.persistence.EquipoRepositoryImpl;
import com.polla.bo.polla.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/polla")
@RequiredArgsConstructor
public class PollaController {

    private static final String SUCCESS_MESSAGE = "Operación realizada exitosamente";
    private static final String ERROR_MESSAGE = "Error en la solicitud";

    private final EquipoRepositoryImpl equipoRepository;


    @PostMapping("/lst-equipos")
    public ResponseEntity<?> obtenerEmpresas() {
        try {
            List<Equipo> equipo = equipoRepository.getAll();

            if (equipo.isEmpty()) {
                return buildSuccessResponse(HttpStatus.NO_CONTENT, "No se encontraron empresas");
            }

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(SUCCESS_MESSAGE, equipo, HttpStatus.OK.value()));

        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody Equipo mb) {

        String accion = mb.getCodEquipo() == 0 ? "I" : "U";

        try {
            boolean operationSuccess =equipoRepository.register(mb, accion);

            if (!operationSuccess) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, ERROR_MESSAGE);
            }


            HttpStatus status = accion.equals("I") ? HttpStatus.CREATED : HttpStatus.OK;
            return buildSuccessResponse(status, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.CONFLICT, "Error al registrar el equipo");
        }
    }





    private ResponseEntity<ApiResponse<?>> buildErrorResponse(BindingResult result) {
        String errorMsg = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(ERROR_MESSAGE, errorMsg, HttpStatus.BAD_REQUEST.value()));
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ApiResponse<>(message, null, status.value()));
    }

    private ResponseEntity<ApiResponse<?>> buildSuccessResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ApiResponse<>(message, null, status.value()));
    }
}
