package com.hiekn.metaboot.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidatorForMobile.class)
public @interface UniqueMobile {

    String message() default "{com.hiekn.metaboot.validation.constraints.UniqueMobile.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
