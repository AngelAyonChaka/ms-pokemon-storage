package com.practice.ms_pokemon_storage.exception;

import com.practice.ms_pokemon_storage.exception.PokemonServiceExceptions.PokemonNoEncontradoException;
import com.practice.ms_pokemon_storage.exception.PokemonServiceExceptions.PokemonYaExisteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsGlobalHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsGlobalHandler.class);

    @ExceptionHandler(PokemonNoEncontradoException.class)
    public ResponseEntity<ApiError> manejarPokemonNoEncontrado(PokemonNoEncontradoException exception) {
        return crearRespuesta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PokemonYaExisteException.class)
    public ResponseEntity<ApiError> manejarPokemonYaExiste(PokemonYaExisteException exception) {
        return crearRespuesta(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarArgumentosInvalidos(MethodArgumentNotValidException exception) {
        String mensaje = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return crearRespuesta(HttpStatus.BAD_REQUEST, mensaje);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> manejarMensajeNoLegible(HttpMessageNotReadableException exception) {
        return crearRespuesta(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud no tiene un formato valido");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarExcepcionNoControlada(Exception exception) {
        LOGGER.error("Ocurrio una excepcion no controlada", exception);
        return crearRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrio un error interno en el servidor"
        );
    }

    private ResponseEntity<ApiError> crearRespuesta(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status)
                .body(new ApiError(
                        Instant.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        mensaje
                ));
    }

    public record ApiError(
            Instant timestamp,
            int status,
            String error,
            String message
    ) {
    }
}
