package com.intern_project.global.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "종료 시간은 시작 시간보다 과거일 수 없습니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
