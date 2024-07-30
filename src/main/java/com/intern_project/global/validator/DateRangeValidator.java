package com.intern_project.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String startDate = (String) new BeanWrapperImpl(o).getPropertyValue("startDate");
        String endDate = (String) new BeanWrapperImpl(o).getPropertyValue("endDate");

        if (startDate != null || endDate != null) {
            try {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                return !start.isAfter(end);
            } catch (DateTimeParseException e) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("잘못된 날짜 형식입니다.")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
