package br.com.catalisa.ZuPlaceApi.exception;

public class SpaceNotFoundException extends RuntimeException{
    public SpaceNotFoundException(Long id) {
        super("Espaço não encontrado com o ID: " + id);
    }
}
