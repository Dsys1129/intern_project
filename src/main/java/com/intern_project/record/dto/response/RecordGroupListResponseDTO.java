package com.intern_project.record.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecordGroupListResponseDTO {
    private Long groupId;
    private String painArea;
    private Integer count;
    private LocalDateTime lastRecordDate;

    public RecordGroupListResponseDTO(Long groupId, String painArea, Integer count, LocalDateTime lastRecordDate) {
        this.groupId = groupId;
        this.painArea = painArea;
        this.count = count;
        this.lastRecordDate = lastRecordDate;
    }
}
