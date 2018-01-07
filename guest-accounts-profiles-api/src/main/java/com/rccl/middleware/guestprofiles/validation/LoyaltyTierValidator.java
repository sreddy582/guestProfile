package com.rccl.middleware.guestprofiles.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoyaltyTierValidator implements ConstraintValidator<LoyaltyTier, Enum> {
    
    @Override
    public void initialize(LoyaltyTier constraintAnnotation) {
        // No-op.
    }
    
    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        
        return !"invalid".equalsIgnoreCase(value.toString());
    }
}
