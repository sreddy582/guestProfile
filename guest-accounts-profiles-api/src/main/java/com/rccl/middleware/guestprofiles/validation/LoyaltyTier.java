package com.rccl.middleware.guestprofiles.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoyaltyTierValidator.class)
@Documented
public @interface LoyaltyTier {
    
    Class<?>[] groups() default {};
    
    String message() default "The given loyalty tier value is invalid.";
    
    String format() default "yyyyMMdd";
    
    Class<? extends Payload>[] payload() default {};
}
