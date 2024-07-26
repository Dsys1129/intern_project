package com.intern_project.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class BaseResponseDTO<T> {

    private int code;
    private T data;

    private BaseResponseDTO(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> BaseResponseDTO<T> createBaseResponseWithDataStatus200(T data) {
        return new BaseResponseDTO<>(HttpStatus.OK.value(), data);
    }

    public static <T> BaseResponseDTO<T> createBaseResponseWithoutDataStatus200() {
        return new BaseResponseDTO<>(HttpStatus.OK.value(), null);
    }

    public static <T> BaseResponseDTO<T> createBaseResponseWithDataStatus201(T data) {
        return new BaseResponseDTO<>(HttpStatus.CREATED.value(), data);
    }

    public static <T> BaseResponseDTO<T> createBaseResponseWithoutDataStatus201() {
        return new BaseResponseDTO<>(HttpStatus.CREATED.value(), null);
    }
}
