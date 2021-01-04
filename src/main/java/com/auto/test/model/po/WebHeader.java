package com.auto.test.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "WebHeader", description = "请求头")
public class WebHeader implements Serializable {
  
  private String key;
  private String value;
  private static final long serialVersionUID = 1L;
  
  public WebHeader(String ContentTypeValue) {
    this.key = "Content-Type";
    this.value = ContentTypeValue;
  }
}