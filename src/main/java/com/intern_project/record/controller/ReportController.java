package com.intern_project.record.controller;

import com.intern_project.global.BaseResponseDTO;
import com.intern_project.record.dto.request.RecordReportListRequestDTO;
import com.intern_project.record.dto.response.RecordHistoryListResponseDTO;
import com.intern_project.record.service.RecordService;
import com.intern_project.user.domain.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReportController {

    private final RecordService recordService;

    @GetMapping("/records/report/download")
    public String downloadReport(@Valid @ModelAttribute RecordReportListRequestDTO requestDTO, Model model, UserInfo userInfo) {
        BaseResponseDTO<List<RecordHistoryListResponseDTO>> response = recordService.getRecordReportList(requestDTO, userInfo.getUserId());
        model.addAttribute("request", requestDTO);
        model.addAttribute("data", response.getData());
        return "report";
    }
}
