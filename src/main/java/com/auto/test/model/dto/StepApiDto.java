package com.auto.test.model.dto;

import com.auto.test.entity.TAutoInterface;
import com.auto.test.entity.TAutoStepInterface;
import com.auto.test.entity.TAutoTestcase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "StepApiDto", description = "测试用例步骤参数")
public class StepApiDto extends TAutoStepInterface {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(hidden = true)
  private TAutoInterface api;
}
