package com.intern_project.record.service;

import com.intern_project.global.BaseResponseDTO;
import com.intern_project.record.domain.Record;
import com.intern_project.record.domain.RecordDetail;
import com.intern_project.record.domain.Symptom;
import com.intern_project.record.dto.request.FollowupRecordRequestDTO;
import com.intern_project.record.dto.request.InitialRecordRequestDTO;
import com.intern_project.record.dto.request.RecordHistoryListRequestDTO;
import com.intern_project.record.dto.response.RecordDetailResponseDTO;
import com.intern_project.record.dto.response.RecordHistoryListResponseDTO;
import com.intern_project.record.mapper.RecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

    private final RecordMapper recordMapper;

    @Transactional
    public BaseResponseDTO createInitialRecord(InitialRecordRequestDTO requestDTO) {
        Record record = requestDTO.toRecord(1L);
        recordMapper.saveRecord(record);

        RecordDetail recordDetail = requestDTO.toRecordDetail(record.getId(), LocalDateTime.now());
        recordMapper.saveRecordDetail(recordDetail);

        List<Integer> symptoms = requestDTO.getSymptoms();
        recordMapper.saveSymptoms(recordDetail.getId(), symptoms);
        return BaseResponseDTO.createBaseResponseWithoutDataStatus201();
    }

    @Transactional
    public BaseResponseDTO createFollowupRecord(Long recordGroupId, FollowupRecordRequestDTO requestDTO) {
        RecordDetail recordDetail = requestDTO.toRecordDetail(recordGroupId, LocalDateTime.now());
        recordMapper.saveRecordDetail(recordDetail);
        List<Integer> symptoms = requestDTO.getSymptoms();
        recordMapper.saveSymptoms(recordDetail.getId(), symptoms);
        return BaseResponseDTO.createBaseResponseWithoutDataStatus201();
    }

    public BaseResponseDTO<List<Symptom>> getSymptoms() {
        List<Symptom> symptomResults = recordMapper.getSymptoms();
        return BaseResponseDTO.createBaseResponseWithDataStatus200(symptomResults);
    }

    public BaseResponseDTO<List<RecordHistoryListResponseDTO>> getRecords(RecordHistoryListRequestDTO requestDTO) {
        List<RecordHistoryListResponseDTO> recordsByUserIdAndByYearMonth = recordMapper.getRecordsByUserIdAndPainAreaAndYearMonth(1L, requestDTO.getPainArea(), requestDTO.getYearMonth());
        return BaseResponseDTO.createBaseResponseWithDataStatus200(recordsByUserIdAndByYearMonth);
    }

    public BaseResponseDTO<RecordDetailResponseDTO> getRecordDetail(Long recordId) {
        RecordDetailResponseDTO recordDetail = recordMapper.getRecordsByRecordId(recordId);
        return BaseResponseDTO.createBaseResponseWithDataStatus200(recordDetail);
    }
}
