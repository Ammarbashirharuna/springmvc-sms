package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;

public interface ResultService {
    List<ResultDto> getAllResults();
    ResultDto getResultById(UUID id);
    ResultModel saveResult(ResultDto resultDto);
    void updateResult(ResultDto resultDto);
    void deleteResult(UUID id);
}
