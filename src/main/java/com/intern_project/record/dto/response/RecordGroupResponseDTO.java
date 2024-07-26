package com.intern_project.record.dto.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecordGroupResponseDTO {
    private Integer totalCount;
    private Integer daysSinceLastRecord;
    private List<RecordGroupListResponseDTO> groups = new ArrayList<>();

    public RecordGroupResponseDTO(Integer totalCount, Integer daysSinceLastRecord) {
        this.totalCount = totalCount;
        this.daysSinceLastRecord = daysSinceLastRecord;
    }

    public void setGroups(List<RecordGroupListResponseDTO> groups) {
        this.groups = groups;
    }
}
