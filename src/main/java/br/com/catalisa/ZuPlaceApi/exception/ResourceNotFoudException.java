package br.com.catalisa.ZuPlaceApi.exception;

public class ResourceNotFoudException extends RuntimeException{
    public ResourceNotFoudException(Long id) {
        super("Recurso não encontrado com o ID: " + id);
    }
}
