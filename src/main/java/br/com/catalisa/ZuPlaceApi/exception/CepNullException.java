package br.com.catalisa.ZuPlaceApi.exception;

public class CepNullException extends RuntimeException {
    public CepNullException(String mensagem) {
        super(mensagem);
    }
}
