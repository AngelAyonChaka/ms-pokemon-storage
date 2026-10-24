package com.practice.ms_pokemon_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practice.ms_pokemon_storage.models.Pokemon;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findByName(String name);

    boolean existsByName(String name);
}