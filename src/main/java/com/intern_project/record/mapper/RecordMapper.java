package com.intern_project.record.mapper;

import com.intern_project.record.domain.Record;
import com.intern_project.record.domain.RecordDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    void saveRecord(Record record);

    void saveRecordDetail(RecordDetail recordDetail);

    void saveSymptoms(Long recordDetailId, List<Integer> symptoms);


}
