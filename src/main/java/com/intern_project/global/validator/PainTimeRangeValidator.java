package com.intern_project.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class PainTimeRangeValidator implements ConstraintValidator<ValidPainTimeRange, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        LocalTime painStartTime = (LocalTime) new BeanWrapperImpl(o).getPropertyValue("painStartTime");
        LocalTime painEndTime = (LocalTime) new BeanWrapperImpl(o).getPropertyValue("painEndTime");
        return painEndTime.isAfter(painStartTime);
    }
}
