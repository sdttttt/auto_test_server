package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExtractResult implements Serializable {
  private String dataSource;
  private String extractExpress;
  private String varName;
  private String realType;
  private String realValue;
  private static final long serialVersionUID = 1L;
}