package br.com.catalisa.ZuPlaceApi.validation;

import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;
import static java.lang.annotation.ElementType.*;
@Documented
@ConstraintComposition(OR)
@CPF
@CNPJ
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface CpfOrCnpj {
    String message () default "O campo deve ser formatado como cpf ou cnpj";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload () default {};
    @Documented
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @interface List{
        CpfOrCnpj[] value();
    }
}
