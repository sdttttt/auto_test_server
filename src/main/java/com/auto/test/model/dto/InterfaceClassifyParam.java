package com.auto.test.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "InterfaceClassifyParam", description = "基础列表参数")
public class InterfaceClassifyParam extends PageParamBase {
  /**
   * 接口名称
   */
  @ApiModelProperty(value = "名称,字段排序为name,create_time,update_time")
  protected String name;
}
