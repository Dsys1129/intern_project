package com.intern_project.record.dto.response;

import lombok.Getter;

@Getter
public class RecordHistoryListResponseDTO {
    private Long id;
    private String note;
    private Integer painIntensity;
    private String painMood;
    private String createdAt;

    public RecordHistoryListResponseDTO(Long id, String note, Integer painIntensity, String painMood, String createdAt) {
        this.id = id;
        this.note = note;
        this.painIntensity = painIntensity;
        this.painMood = painMood;
        this.createdAt = createdAt;
    }
}
