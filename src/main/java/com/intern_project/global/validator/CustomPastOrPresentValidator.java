package com.intern_project.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class CustomPastOrPresentValidator implements ConstraintValidator<CustomPastOrPresent, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(value, FORMATTER);
            return !parsedDate.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("잘못된 날짜 형식입니다.")
                    .addConstraintViolation();
            return false;
        }
    }
}
