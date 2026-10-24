package com.practice.ms_pokemon_storage.service.impl;

import com.practice.ms_pokemon_storage.dto.PokemonRequest;
import com.practice.ms_pokemon_storage.dto.PokemonResponse;
import com.practice.ms_pokemon_storage.exception.PokemonServiceExceptions.PokemonNoEncontradoException;
import com.practice.ms_pokemon_storage.models.Pokemon;
import com.practice.ms_pokemon_storage.repository.PokemonRepository;
import com.practice.ms_pokemon_storage.service.PokemonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    // Inyeccion de dependencias por constructor
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonResponse guardarPokemon(PokemonRequest request) {
        // 1. Convertimos el DTO de entrada (Request) a nuestra Entity
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonId(request.pokemonId());
        pokemon.setName(request.name());
        pokemon.setBaseExperience(request.baseExperience());
        pokemon.setHeight(request.height());
        pokemon.setOrder(request.order());
        pokemon.setWeight(request.weight());
        pokemon.setAbility(request.ability());

        // 2. Guardamos la Entity usando el Repository (JpaRepository ya trae el metodo .save())
        Pokemon pokemonGuardado = pokemonRepository.save(pokemon);

        // 3. Convertimos la Entity ya guardada (con su ID generado) a nuestro DTO de salida
        return mapearAResponse(pokemonGuardado);
    }

    @Override
    public List<PokemonResponse> obtenerTodos() {
        // findAll() ya viene incluido gratis al heredar de JpaRepository
        return pokemonRepository.findAll().stream()
                .map(this::mapearAResponse) // Usamos Stream API (Tema 15) para convertir cada Entity a Response
                .collect(Collectors.toList());
    }

    @Override
    public PokemonResponse obtenerPorNombre(String name) {
        // findByName() regresa un Optional, lo manejamos con orElseThrow (Tema 16 + Tema 10 juntos)
        Pokemon pokemon = pokemonRepository.findByName(name)
                .orElseThrow(() -> new PokemonNoEncontradoException(
                        "No se encontro el pokemon con nombre: " + name));

        return mapearAResponse(pokemon);
    }

    // Metodo privado de apoyo para no repetir el mismo mapeo en varios lugares
    private PokemonResponse mapearAResponse(Pokemon pokemon) {
        return new PokemonResponse(
                pokemon.getId(),
                pokemon.getPokemonId(),
                pokemon.getName(),
                pokemon.getBaseExperience(),
                pokemon.getHeight(),
                pokemon.getOrder(),
                pokemon.getWeight(),
                pokemon.getAbility()
        );
    }
}