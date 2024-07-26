package com.intern_project.record.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
public class RecordDetail {
    private Long id;

    private Long recordGroupId;

    private List<Integer> symptoms;

    private LocalTime painStartTime;

    private LocalTime painEndTime;

    private Integer painIntensity;

    private Integer painMood;

    private String note;

    private boolean isInitialRecord;

    private LocalDateTime createdAt;

    private RecordDetail(Long recordGroupId, List<Integer> symptoms, LocalTime painStartTime, LocalTime painEndTime, Integer painIntensity, Integer painMood, String note, boolean isInitialRecord, LocalDateTime createdAt) {
        this.recordGroupId = recordGroupId;
        this.symptoms = symptoms;
        this.painStartTime = painStartTime;
        this.painEndTime = painEndTime;
        this.painIntensity = painIntensity;
        this.painMood = painMood;
        this.note = note;
        this.isInitialRecord = isInitialRecord;
        this.createdAt = createdAt;
    }

    public static RecordDetail createInitialRecordDetail(Long recordGroupId, List<Integer> symptoms, Integer painIntensity, Integer painMood, String note, LocalDateTime createdAt){
        return new RecordDetail(recordGroupId, symptoms, null, null, painIntensity, painMood, note, true, createdAt);
    }

    public static RecordDetail createFollowupRecordDetail(Long recordGroupId, List<Integer> symptoms, LocalTime painStartTime, LocalTime painEndTime, Integer painIntensity, Integer painMood, String note, LocalDateTime createdAt){
        return new RecordDetail(recordGroupId, symptoms, painStartTime, painEndTime, painIntensity, painMood, note, false, createdAt);
    }
}
