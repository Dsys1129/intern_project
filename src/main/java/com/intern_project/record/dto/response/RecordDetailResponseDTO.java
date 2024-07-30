package com.intern_project.record.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class RecordDetailResponseDTO {
    private Long id;
    private Long userId;
    private String painArea;
    private Integer painAreaDetail;
    private Integer painIntensity;
    private String note;
    private LocalTime painStartTime;
    private LocalTime painEndTime;
    private LocalDateTime painStartDateTime;
    private String painDuration;

    public RecordDetailResponseDTO(Long id, Long userId,String painArea, Integer painAreaDetail, Integer painIntensity, String note, LocalTime painStartTime, LocalTime painEndTime, LocalDateTime painStartDateTime, String painDuration) {
        this.id = id;
        this.userId = userId;
        this.painArea = painArea;
        this.painAreaDetail = painAreaDetail;
        this.painIntensity = painIntensity;
        this.note = note;
        this.painStartTime = painStartTime;
        this.painEndTime = painEndTime;
        this.painStartDateTime = painStartDateTime;
        this.painDuration = painDuration;
    }
}
