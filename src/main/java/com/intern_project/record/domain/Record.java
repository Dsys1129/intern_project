package com.intern_project.record.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Record {

    private Long id;

    private Long userId;

    private String painArea;

    private Integer painAreaDetail;

    private LocalDateTime painStartDateTime;

    private String painStartPattern;

    private String painDuration;

    public Record(Long userId, String painArea, Integer painAreaDetail,LocalDateTime painStartDateTime, String painStartPattern, String painDuration) {
        this.userId = userId;
        this.painArea = painArea;
        this.painAreaDetail = painAreaDetail;
        this.painStartDateTime = painStartDateTime;
        this.painStartPattern = painStartPattern;
        this.painDuration = painDuration;
    }
}
