package br.com.catalisa.ZuPlaceApi.exception;

public class UserNotFoudException extends RuntimeException{
    public UserNotFoudException(Long id) {
        super("Usuário não encontrado com o ID: " + id);
    }
}
