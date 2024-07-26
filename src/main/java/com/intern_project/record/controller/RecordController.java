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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class RecordController implements SwaggerApi{

    private final RecordService recordService;

    @Override
    @PostMapping("/records")
    public ResponseEntity<BaseResponseDTO> createInitialRecord(@Valid @RequestBody InitialRecordRequestDTO requestDTO) {
        BaseResponseDTO response = recordService.createInitialRecord(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
