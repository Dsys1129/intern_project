package com.intern_project.record.service;

import com.intern_project.global.BaseResponseDTO;
import com.intern_project.record.domain.Record;
import com.intern_project.record.domain.RecordDetail;
import com.intern_project.record.domain.Symptom;
import com.intern_project.record.dto.request.FollowupRecordRequestDTO;
import com.intern_project.record.dto.request.InitialRecordRequestDTO;
import com.intern_project.record.dto.request.RecordHistoryListRequestDTO;
import com.intern_project.record.dto.request.RecordReportListRequestDTO;
import com.intern_project.record.dto.response.RecordDetailResponseDTO;
import com.intern_project.record.dto.response.RecordGroupListResponseDTO;
import com.intern_project.record.dto.response.RecordGroupResponseDTO;
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
    public BaseResponseDTO createInitialRecord(InitialRecordRequestDTO requestDTO, Long userId) {
        Record record = requestDTO.toRecord(userId);
        recordMapper.saveRecord(record);

        RecordDetail recordDetail = requestDTO.toRecordDetail(record.getId(), LocalDateTime.now());
        recordMapper.saveRecordDetail(recordDetail);

        List<Integer> symptoms = requestDTO.getSymptoms();
        recordMapper.saveSymptoms(recordDetail.getId(), symptoms);
        return BaseResponseDTO.createBaseResponseWithoutDataStatus201();
    }

    @Transactional
    public BaseResponseDTO createFollowupRecord(Long recordGroupId, FollowupRecordRequestDTO requestDTO, Long userId) {

        Record findRecord = recordMapper.findRecordBy(recordGroupId);
        if (findRecord == null) {
            throw new IllegalArgumentException("해당하는 통증 기록이 없습니다.");
        }

        if (!findRecord.getUserId().equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

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

    public BaseResponseDTO<List<RecordHistoryListResponseDTO>> getRecords(RecordHistoryListRequestDTO requestDTO, Long userId) {
        List<RecordHistoryListResponseDTO> recordsByUserIdAndByYearMonth = recordMapper.findRecordHistories(userId, requestDTO.getPainArea(), requestDTO.getYearMonth());
        return BaseResponseDTO.createBaseResponseWithDataStatus200(recordsByUserIdAndByYearMonth);
    }

    public BaseResponseDTO<RecordDetailResponseDTO> getRecordDetail(Long recordId, Long userId) {
        RecordDetailResponseDTO recordDetail = recordMapper.getRecordsByRecordId(recordId);
        if (recordDetail == null) {
            throw new IllegalArgumentException("해당하는 통증 기록이 없습니다.");
        }
        if (!recordDetail.getUserId().equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return BaseResponseDTO.createBaseResponseWithDataStatus200(recordDetail);
    }

    public BaseResponseDTO<RecordGroupResponseDTO> getRecordGroups(Long userId) {
        List<RecordGroupListResponseDTO> recordGroupsByUserId = recordMapper.getRecordGroupsByUserId(userId);
        RecordGroupResponseDTO result = recordMapper.getTotalPainRecordsAndLastDateByUserId(userId);
        result.setGroups(recordGroupsByUserId);

        return BaseResponseDTO.createBaseResponseWithDataStatus200(result);
    }

    public BaseResponseDTO<List<RecordHistoryListResponseDTO>> getRecordReportList(RecordReportListRequestDTO requestDTO, Long userId) {
        List<RecordHistoryListResponseDTO> result = recordMapper.
                findRecordReports(userId, requestDTO.getPainArea(),
                        requestDTO.getStartDate(), requestDTO.getEndDate());

        return BaseResponseDTO.createBaseResponseWithDataStatus200(result);
    }

    public BaseResponseDTO<List<Symptom>> getLastSelectedSymptoms(Long recordGroupId, Long userId) {
        List<Symptom> result = recordMapper.getLastSelectedSymptomsByGroupIdAndUserId(recordGroupId, userId);
        return BaseResponseDTO.createBaseResponseWithDataStatus200(result);
    }
}
