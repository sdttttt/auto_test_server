package com.auto.test.model.dto;

import com.auto.test.entity.TAutoTestcase;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "TestcaseApiDto", description = "测试用例参数")
public class TestcaseApiDto extends TAutoTestcase {
  private static final long serialVersionUID = 1L;
  private List<StepApiDto> testSteps;
}
