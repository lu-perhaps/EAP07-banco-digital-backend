package com.udea.bancodigital.common.exception;

import com.udea.bancodigital.common.filter.TraceIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Metodo de apoyo: arma la respuesta con la misma forma siempre,
    // para no repetir la construccion del mapa en cada handler.
    private ResponseEntity<Map<String, Object>> construirRespuesta(
            HttpStatus status, String errorCode, String message,
            Object details, HttpServletRequest request
    ) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("errorCode", errorCode);
        respuesta.put("message", message);
        respuesta.put("details", details);
        respuesta.put("traceId", request.getAttribute(TraceIdFilter.TRACE_ID_ATTR));

        return ResponseEntity.status(status).body(respuesta);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        Map<String, String> detalles = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                detalles.put(error.getField(), error.getDefaultMessage())
        );

        return construirRespuesta(
                HttpStatus.BAD_REQUEST, "VALIDATION_ERROR",
                "Los datos enviados no son validos", detalles, request
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> manejarCredencialesInvalidas(
            BadCredentialsException exception, HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS",
                exception.getMessage(), null, request
        );
    }

    // Red de seguridad: cualquier excepcion no contemplada explicitamente
    // tambien responde en el mismo formato uniforme, nunca con un stacktrace crudo.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGeneral(
            Exception exception, HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "Ocurrio un error inesperado", null, request
        );
    }
}