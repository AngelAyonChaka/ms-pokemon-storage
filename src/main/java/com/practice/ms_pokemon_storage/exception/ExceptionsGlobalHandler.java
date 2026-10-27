package com.practice.ms_pokemon_storage.exception;

import com.practice.ms_pokemon_storage.exception.PokemonServiceExceptions.PokemonNoEncontradoException;
import com.practice.ms_pokemon_storage.exception.PokemonServiceExceptions.PokemonListaVaciaException;
import com.practice.ms_pokemon_storage.exception.PokemonServiceExceptions.PokemonYaExisteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsGlobalHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsGlobalHandler.class);

    @ExceptionHandler(PokemonNoEncontradoException.class)
    public ResponseEntity<ApiError> manejarPokemonNoEncontrado(PokemonNoEncontradoException exception) {
        LOGGER.warn("Pokemon no encontrado: {}", exception.getMessage());
        return crearRespuesta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PokemonListaVaciaException.class)
    public ResponseEntity<ApiError> manejarListaDePokemonesVacia(PokemonListaVaciaException exception) {
        LOGGER.warn("Lista de pokemones vacia: {}", exception.getMessage());
        return crearRespuesta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PokemonYaExisteException.class)
    public ResponseEntity<ApiError> manejarPokemonYaExiste(PokemonYaExisteException exception) {
        LOGGER.warn("Pokemon duplicado: {}", exception.getMessage());
        return crearRespuesta(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarArgumentosInvalidos(MethodArgumentNotValidException exception) {
        String mensaje = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        LOGGER.warn("Argumentos invalidos: {}", mensaje);
        return crearRespuesta(HttpStatus.BAD_REQUEST, mensaje);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> manejarMensajeNoLegible(HttpMessageNotReadableException exception) {
        String mensaje = "El cuerpo de la solicitud no tiene un formato valido";
        LOGGER.warn("{} Detalle: {}", mensaje, exception.getMessage());
        return crearRespuesta(HttpStatus.BAD_REQUEST, mensaje);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> manejarRecursoNoEncontrado(NoResourceFoundException exception) {
        String mensaje = "La ruta solicitada no existe";
        LOGGER.warn("{}: {}", mensaje, exception.getResourcePath());
        return crearRespuesta(HttpStatus.NOT_FOUND, mensaje);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarExcepcionNoControlada(Exception exception) {
        LOGGER.error(
                "Ocurrio una excepcion no controlada. Tipo: {}. Mensaje: {}",
                exception.getClass().getName(),
                exception.getMessage()
        );
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
