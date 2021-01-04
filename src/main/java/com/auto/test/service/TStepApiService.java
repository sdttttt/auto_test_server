package com.auto.test.service;

import com.auto.test.entity.TAutoStepInterface;
import com.auto.test.entity.TAutoTestcase;
import com.auto.test.model.dto.StepApiDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TStepApiService extends IService<TAutoStepInterface> {
  
  void saveSteps(List<StepApiDto> testSteps);
  
  Boolean removeByTestCaseId(String testCaseId);
}












