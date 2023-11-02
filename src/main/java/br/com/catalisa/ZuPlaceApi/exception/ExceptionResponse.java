package br.com.catalisa.ZuPlaceApi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse implements Serializable {
    private static final Long serialVersionUID = 1L;
    private LocalDate timestamp;
    private String message;
}
