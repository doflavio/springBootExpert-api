package io.github.doflavio.validation.ConstraintValidation;

import io.github.doflavio.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator  implements ConstraintValidator<NotEmptyList, List> {

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        //constraintAnnotation.message();
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty();
    }
}
