package com.intern_project.record.dto.request;

import com.intern_project.global.validator.ValidPainTimeRange;
import com.intern_project.record.domain.RecordDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "통증 기록 생성을 요청하는 DTO")
@ValidPainTimeRange
@AllArgsConstructor
@Data
public class FollowupRecordRequestDTO {

    @Schema(description = "통증 증상", minimum = "0", defaultValue = "[0]")
    @Size(min = 1, message = "증상은 하나 이상 선택되어야 합니다.")
    @NotNull(message = "증상은 필수 입력 사항입니다.")
    private List<Integer> symptoms;

    @Schema(description = "통증 시작 시간", pattern = "HH:mm:ss", defaultValue = "11:57:33")
//    @PastOrPresent(message = "유효하지 않은 시간입니다.")
    @NotNull(message = "통증 시작 시간은 필수 입력 사항입니다.")
    private LocalTime painStartTime;

    @Schema(description = "통증 종료 시간", pattern = "HH:mm:ss", defaultValue = "12:00:33")
//    @PastOrPresent(message = "유효하지 않은 시간입니다.")
    @NotNull(message = "통증 종료 시간은 필수 입력 사항입니다.")
    private LocalTime painEndTime;

    @Schema(description = "통증의 정도", minimum = "0", maximum = "10")
    @Max(value = 10, message = "통증 정도는 0~10 입니다.")
    @PositiveOrZero(message = "통증 정도는 음수일 수 없습니다.")
    @NotNull(message = "통증 정도는 필수 입력 사항입니다.")
    private Integer painIntensity;

    @PositiveOrZero(message = "통증 기록 기분은 음수일 수 없습니다.")
    @Schema(description = "통증 기록 기분", allowableValues = {"0", "1", "2"})
    @NotNull(message = "기분은 필수 입력 사항입니다.")
    private Integer painMood;

    @Schema(description = "메모", defaultValue = "손끝이 저림")
    @Size(max = 100, message = "메모는 최소 1자에서 100자 입니다.")
    @NotBlank(message = "메모는 필수 입력 사항입니다.")
    private String note;

    public RecordDetail toRecordDetail(Long recordGroupId, LocalDateTime createdAt) {
        return RecordDetail.createFollowupRecordDetail(recordGroupId, painStartTime, painEndTime, painIntensity, painMood, note, createdAt);
    }
}
