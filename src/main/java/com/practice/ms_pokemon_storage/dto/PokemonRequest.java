package com.practice.ms_pokemon_storage.dto;

public record PokemonRequest(
    Integer pokemonId,
    String name,
    Integer baseExperience,
    Integer height,
    Integer order,
    Integer weight,
    String ability
    ) {
}