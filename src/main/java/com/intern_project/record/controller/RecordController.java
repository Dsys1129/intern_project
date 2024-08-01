package com.intern_project.record.controller;

import com.intern_project.global.BaseResponseDTO;
import com.intern_project.record.domain.Symptom;
import com.intern_project.record.dto.request.FollowupRecordRequestDTO;
import com.intern_project.record.dto.request.InitialRecordRequestDTO;
import com.intern_project.record.dto.request.RecordHistoryListRequestDTO;
import com.intern_project.record.dto.request.RecordReportListRequestDTO;
import com.intern_project.record.dto.response.RecordDetailResponseDTO;
import com.intern_project.record.dto.response.RecordGroupResponseDTO;
import com.intern_project.record.dto.response.RecordHistoryListResponseDTO;
import com.intern_project.record.service.RecordService;
import com.intern_project.user.domain.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class RecordController implements SwaggerApi{

    private final RecordService recordService;

    @Override
    @PostMapping("/records")
    public ResponseEntity<BaseResponseDTO> createInitialRecord(@Valid @RequestBody InitialRecordRequestDTO requestDTO, UserInfo userInfo) {
        BaseResponseDTO response = recordService.createInitialRecord(requestDTO, userInfo.getUserId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    @PostMapping("/records/{groupId}")
    public ResponseEntity<BaseResponseDTO> createFollowupRecord(@PathVariable Long groupId, @Valid @RequestBody FollowupRecordRequestDTO requestDTO, UserInfo userInfo) {
        BaseResponseDTO response = recordService.createFollowupRecord(groupId, requestDTO, userInfo.getUserId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    @GetMapping("/symptoms")
    public ResponseEntity<BaseResponseDTO<List<Symptom>>> getSymptoms() {
        BaseResponseDTO<List<Symptom>> response = recordService.getSymptoms();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    @GetMapping("/records")
    public ResponseEntity<BaseResponseDTO<List<RecordHistoryListResponseDTO>>> getRecords(@Valid @ModelAttribute RecordHistoryListRequestDTO requestDTO, UserInfo userInfo) {
        log.info(userInfo.toString());
        BaseResponseDTO<List<RecordHistoryListResponseDTO>> response = recordService.getRecords(requestDTO, userInfo.getUserId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    @GetMapping("/records/{recordId}")
    public ResponseEntity<BaseResponseDTO<RecordDetailResponseDTO>> getRecordDetail(@PathVariable Long recordId, UserInfo userInfo) {
        BaseResponseDTO<RecordDetailResponseDTO> response = recordService.getRecordDetail(recordId, userInfo.getUserId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<BaseResponseDTO<RecordGroupResponseDTO>> getRecordGroups(UserInfo userInfo) {
        BaseResponseDTO<RecordGroupResponseDTO> response = recordService.getRecordGroups(userInfo.getUserId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    @GetMapping("/records/report")
    public ResponseEntity<BaseResponseDTO<List<RecordHistoryListResponseDTO>>> getRecordReportList(@Valid @ModelAttribute RecordReportListRequestDTO requestDTO, UserInfo userInfo) {
        BaseResponseDTO<List<RecordHistoryListResponseDTO>> response = recordService.getRecordReportList(requestDTO, userInfo.getUserId());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    @GetMapping("/records/{groupId}/symptoms")
    public ResponseEntity<BaseResponseDTO<List<Symptom>>> getLastSelectedSymptoms(@PathVariable(name = "groupId") Long recordGroupId, UserInfo userInfo) {
        BaseResponseDTO response = recordService.getLastSelectedSymptoms(recordGroupId, userInfo.getUserId());
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
