package com.practice.ms_pokemon_storage.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pokemon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pokemon_id", nullable = false)
    private Integer pokemonId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "base_experience")
    private Integer baseExperience;

    @Column
    private Integer height;

    @Column(name = "pokemon_order")
    private Integer order;

    @Column
    private Integer weight;

    @Column(length = 100)
    private String ability;
}