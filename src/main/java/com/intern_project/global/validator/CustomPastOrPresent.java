package com.intern_project.global.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomPastOrPresentValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPastOrPresent {
    String message() default "현재 날짜보다 미래의 값을 입력할 수 없습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
