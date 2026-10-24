package com.practice.ms_pokemon_storage.controller;

import com.practice.ms_pokemon_storage.dto.PokemonRequest;
import com.practice.ms_pokemon_storage.dto.PokemonResponse;
import com.practice.ms_pokemon_storage.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping
    public ResponseEntity<PokemonResponse> guardarPokemon(@RequestBody PokemonRequest request) {
        PokemonResponse response = pokemonService.guardarPokemon(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PokemonResponse>> obtenerTodos() {
        List<PokemonResponse> response = pokemonService.obtenerTodos();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PokemonResponse> obtenerPorNombre(@PathVariable String name) {
        PokemonResponse response = pokemonService.obtenerPorNombre(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}