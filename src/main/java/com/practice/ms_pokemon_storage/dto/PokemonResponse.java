package com.practice.ms_pokemon_storage.dto;

public record PokemonResponse(
    Long id,
    Integer pokemonId,
    String name,
    Integer baseExperience,
    Integer height,
    Integer order,
    Integer weight,
    String ability
    ) {
}