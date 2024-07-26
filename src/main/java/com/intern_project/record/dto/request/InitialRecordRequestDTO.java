package com.intern_project.record.dto.request;

import com.intern_project.record.domain.Record;
import com.intern_project.record.domain.RecordDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "새로운 통증 기록 생성을 요청하는 DTO")
@AllArgsConstructor
@Getter
public class InitialRecordRequestDTO {

    @Schema(description = "통증 부위", defaultValue = "손/손가락")
    @NotBlank(message = "통증 부위는 필수 입력 사항입니다.")
    private String painArea;

    @Schema(description = "통증 증상", minimum = "0", defaultValue = "[0]")
    @Size(min = 1, message = "증상은 하나 이상 선택되어야 합니다.")
    @NotNull(message = "증상은 필수 입력 사항입니다.")
    private List<Integer> symptoms;

    @Schema(description = "통증 위치", minimum = "0", defaultValue = "1")
    @Min(value = 0 , message = "통증 위치는 음수일 수 없습니다.")
    @NotNull(message = "통증 위치는 필수 입력 사항입니다.")
    private Integer painAreaDetail;

    @Schema(description = "통증 시작 날짜", defaultValue = "2024-07-25T00:00:00")
    @NotNull(message = "통증 시작 날짜는 필수 입력 사항입니다.")
    @PastOrPresent(message = "통증 시작 날짜는 현재 혹은 과거의 시간만 가능합니다.")
    private LocalDateTime painStartDateTime;

    @Schema(description = "통증의 정도", minimum = "0", maximum = "10")
    @Max(value = 10, message = "통증 정도는 0~10 입니다.")
    @Positive(message = "통증 정도는 음수일 수 없습니다.")
    @NotNull(message = "통증 정도는 필수 입력 사항입니다.")
    private Integer painIntensity;

    @Min(value = 0, message = "통증 시작 양상은 음수일 수 없습니다.")
    @Schema(description = "통증 시작 양상", examples = {"갑자기","점진적으로"}, allowableValues = {"갑자기","점진적으로"})
    @NotNull(message = "통증 시작 양상은 필수 입력 사항입니다.")
    private Integer painStartPattern;

    @Min(value = 0, message = "통증 지속 시간은 음수일 수 없습니다.")
    @Schema(description = "통증 지속 시간", allowableValues = {"0", "1", "2", "3"})
    @NotNull(message = "통증 지속 시간은 필수 입력 사항입니다.")
    private Integer painDuration;

    @Min(value = 0, message = "통증 기록 기분은 음수일 수 없습니다.")
    @Schema(description = "통증 기록 기분", allowableValues = {"0", "1", "2"})
    @NotNull(message = "기분은 필수 입력 사항입니다.")
    private Integer painMood;

    @Schema(description = "메모", defaultValue = "손끝이 저림")
    @Size(min = 1, max = 100, message = "메모는 최소 1자에서 100자 입니다.")
    @NotBlank(message = "메모는 필수 입력 사항입니다.")
    private String note;

    public Record toRecord(Long userId) {
        return new Record(userId, painArea, painAreaDetail, painStartDateTime, painStartPattern, painDuration);
    }

    public RecordDetail toRecordDetail(Long recordGroupId, LocalDateTime createdAt) {
        return RecordDetail.createInitialRecordDetail(recordGroupId, symptoms, painIntensity, painMood, note, createdAt);
    }
}
