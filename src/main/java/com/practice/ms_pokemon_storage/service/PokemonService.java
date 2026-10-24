package com.practice.ms_pokemon_storage.service;

import com.practice.ms_pokemon_storage.dto.PokemonRequest;
import com.practice.ms_pokemon_storage.dto.PokemonResponse;
import java.util.List;

public interface PokemonService {
    /**
     * Guarda un nuevo Pokémon en la base de datos.
     * @param request Los datos del Pokémon a guardar.
     * @return Los datos del Pokémon guardado.
     */
    PokemonResponse guardarPokemon(PokemonRequest request);

    /**
     * Obtiene todos los Pokémon de la base de datos.
     * @return Una lista con todos los Pokémon.
     */
    List<PokemonResponse> obtenerTodos();

    /**
     * Obtiene un Pokémon por su nombre.
     * @param name El nombre del Pokémon a buscar.
     * @return Los datos del Pokémon encontrado, o null si no se encuentra.
     */
    PokemonResponse obtenerPorNombre(String name);
}
