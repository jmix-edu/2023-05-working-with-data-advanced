package com.company.jmixpm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidProjectDateValidator.class)
@Documented
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface ValidProjectDate {

    String message() default "Start date cannot be later than end date";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
