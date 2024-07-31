package com.intern_project.record.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Hidden
@Data
public class RecordHistoryListRequestDTO {

    private String painArea;

    @NotNull(message = "조회 날짜는 필수 입력 사항입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}", message = "날짜 형식은 yyyy-MM여야 합니다.")
    private String yearMonth;
}
