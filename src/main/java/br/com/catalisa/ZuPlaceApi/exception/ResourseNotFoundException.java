package br.com.catalisa.ZuPlaceApi.exception;

public class ResourseNotFoundException extends RuntimeException{
    public ResourseNotFoundException(String message){
        super(message);
    }
}
