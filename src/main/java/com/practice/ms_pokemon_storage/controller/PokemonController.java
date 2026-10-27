package com.practice.ms_pokemon_storage.controller;

import com.practice.ms_pokemon_storage.dto.PokemonRequest;
import com.practice.ms_pokemon_storage.dto.PokemonResponse;
import com.practice.ms_pokemon_storage.service.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonController.class);

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping
    public ResponseEntity<PokemonResponse> guardarPokemon(@RequestBody PokemonRequest request) {
        LOGGER.info(
                "Iniciando peticion POST /api/pokemon | pokemonId={} | name={}",
                request.pokemonId(),
                request.name()
        );

        PokemonResponse response = pokemonService.guardarPokemon(request);

        LOGGER.info(
                "Peticion POST /api/pokemon completada | status={} | id={} | name={}",
                HttpStatus.CREATED.value(),
                response.id(),
                response.name()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PokemonResponse>> obtenerTodos() {
        LOGGER.info("Iniciando peticion GET /api/pokemon");

        List<PokemonResponse> response = pokemonService.obtenerTodos();

        LOGGER.info(
                "Peticion GET /api/pokemon completada | status={} | cantidad={}",
                HttpStatus.OK.value(),
                response.size()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PokemonResponse> obtenerPorNombre(@PathVariable String name) {
        LOGGER.info("Iniciando peticion GET /api/pokemon/{}", name);

        PokemonResponse response = pokemonService.obtenerPorNombre(name);

        LOGGER.info(
                "Peticion GET /api/pokemon/{} completada | status={} | id={}",
                name,
                HttpStatus.OK.value(),
                response.id()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}