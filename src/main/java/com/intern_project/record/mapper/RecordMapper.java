package com.intern_project.record.mapper;

import com.intern_project.record.domain.Record;
import com.intern_project.record.domain.RecordDetail;
import com.intern_project.record.domain.Symptom;
import com.intern_project.record.dto.response.RecordHistoryListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    void saveRecord(Record record);

    void saveRecordDetail(RecordDetail recordDetail);

    void saveSymptoms(Long recordDetailId, List<Integer> symptoms);

    List<Symptom> getSymptoms();

    List<RecordHistoryListResponseDTO> getRecordsByUserIdAndPainAreaAndYearMonth(Long userId, String painArea, String yearMonth);
}
