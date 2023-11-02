package br.com.catalisa.ZuPlaceApi.exception;

public class ExternalRequestFailureException extends RuntimeException{

    public ExternalRequestFailureException(String msg) {
        super("Request externo falhou: " + msg);
    }
}
