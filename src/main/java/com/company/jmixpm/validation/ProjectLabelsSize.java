package com.company.jmixpm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ProjectLabelsSizeValidator.class)
@Documented
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface ProjectLabelsSize {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "The size of labels must be between {min} and {max}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
