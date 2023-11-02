package br.com.catalisa.ZuPlaceApi.exception;

public class CepFormatException extends RuntimeException {
    public CepFormatException(String mensagem) {
        super(mensagem);
    }
}
