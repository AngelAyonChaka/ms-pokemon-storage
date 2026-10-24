package com.practice.ms_pokemon_storage.exception;

public class PokemonServiceExceptions {
    public static class PokemonNoEncontradoException extends RuntimeException {
        public PokemonNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }

    public static class PokemonYaExisteException extends RuntimeException {
        public PokemonYaExisteException(String mensaje) {
            super(mensaje);
        }
    }
}
