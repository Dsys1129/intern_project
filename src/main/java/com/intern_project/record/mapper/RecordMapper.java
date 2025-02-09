package com.intern_project.record.mapper;

import com.intern_project.record.domain.Record;
import com.intern_project.record.domain.RecordDetail;
import com.intern_project.record.domain.Symptom;
import com.intern_project.record.dto.response.RecordDetailResponseDTO;
import com.intern_project.record.dto.response.RecordGroupListResponseDTO;
import com.intern_project.record.dto.response.RecordGroupResponseDTO;
import com.intern_project.record.dto.response.RecordHistoryListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    void saveRecord(Record record);

    void saveRecordDetail(RecordDetail recordDetail);

    void saveSymptoms(Long recordDetailId, List<Integer> symptoms);

    List<Symptom> getSymptoms();

    List<RecordHistoryListResponseDTO> findRecordHistories(Long userId, String painArea, String yearMonth);

    RecordDetailResponseDTO getRecordsByRecordId(Long recordId);

    List<RecordGroupListResponseDTO> getRecordGroupsByUserId(Long userId);

    RecordGroupResponseDTO getTotalPainRecordsAndLastDateByUserId(Long userId);

    List<RecordHistoryListResponseDTO> findRecordReports(Long userId, String painArea, String startDate, String endDate);

    List<Symptom> getLastSelectedSymptomsByGroupIdAndUserId(Long groupId, Long userId);

    Record findRecordBy(Long recordGroupId);

    RecordDetail findRecordDetailBy(Long recordDetailId);
}
