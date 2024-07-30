package com.intern_project.record.dto.request;

import com.intern_project.global.validator.ValidDateRange;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Hidden
@Getter
@AllArgsConstructor
@ValidDateRange
public class RecordReportListRequestDTO {

    @NotNull(message = "통증 부위는 필수 입력 사항입니다.")
    private String painArea;

    @NotNull(message = "조회 시작 날짜는 필수 입력 사항입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식은 yyyy-MM-dd여야 합니다.")
    private String startDate;

    @NotNull(message = "조회 마지막 날짜는 필수 입력 사항입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식은 yyyy-MM-dd여야 합니다.")
    private String endDate;
}
