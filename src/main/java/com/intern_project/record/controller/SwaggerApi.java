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
import com.intern_project.user.domain.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SwaggerApi {

    @Operation(summary = "통증 기록 최초 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "통증 기록 최초 등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponseDTO> createInitialRecord(InitialRecordRequestDTO requestDTO, UserInfo userInfo);

    @Operation(summary = "통증 기록 재등록", description = "통증 기록 재등록 요청 API",
            parameters = @Parameter(name = "groupId", description = "재등록할 통증 기록 그룹 ID", required = true))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통증 기록 재등록 성공"),
            @ApiResponse(responseCode = "401", description = "Authentication Exception")
    })
    ResponseEntity<BaseResponseDTO> createFollowupRecord(Long groupId, FollowupRecordRequestDTO requestDTO, UserInfo userInfo);

    @Operation(summary = "통증 기록 생성에 필요한 모든 통증 증상 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통증 증상 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponseDTO<List<Symptom>>> getSymptoms();

    @Operation(summary = "특정 부위, 년-월에 해당하는 통증 기록 이력 조회", description = "년-월 (예: 2024-07)에 해당하는 통증 기록 이력 조회",
    parameters = {@Parameter(name = "painArea", allowEmptyValue = true, description = "조회할 부위 (생략 시 전체)", example = "손/손가락", required = false),
            @Parameter(name = "yearMonth", description = "조회할 년-월 yyyy-mm", example = "2024-07", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통증 기록 이력 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponseDTO<List<RecordHistoryListResponseDTO>>> getRecords(RecordHistoryListRequestDTO requestDTO, UserInfo userInfo);

    @Operation(summary = "특정 통증 기록 상세 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통증 기록 상세 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponseDTO<RecordDetailResponseDTO>> getRecordDetail(Long recordId, UserInfo userInfo);

    @Operation(summary = "통증 기록 메인 페이지 조회", description = "통증 기록 탭의 메인 페이지 조회 시 호출되는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통증 기록 메인 페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponseDTO<RecordGroupResponseDTO>> getRecordGroups(UserInfo userInfo);

    @Operation(summary = "통증 기록 보고서 리스트 조회", description = "통증 기록 보고서의 통증 기록 리스트",
    parameters = {@Parameter(name = "painArea", allowEmptyValue = true, description = "조회할 부위 (생략 시 전체)", example = "손/손가락", required = false),
            @Parameter(name = "startDate", description = "조회할 시작일 yyyy-mm-dd", example = "2024-07-01", required = true),
            @Parameter(name = "endDate", description = "조회할 마지막일 yyyy-mm-dd", example = "2024-07-30", required = true)})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통증 기록 리스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponseDTO<List<RecordHistoryListResponseDTO>>> getRecordReportList(RecordReportListRequestDTO requestDTO, UserInfo userInfo);


    @Operation(summary = "통증 재기록 시 마지막에 선택된 증상 리스트 조회", description = "통증 재기록 시 마지막에 선택된 증상 리스트 조회",
            parameters = @Parameter(name = "groupId", description = "재기록 할 통증 기록 그룹 ID", required = true))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통증 기록 리스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponseDTO<List<Symptom>>> getLastSelectedSymptoms(Long groupId, UserInfo userInfo);
}