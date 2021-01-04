package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssertResult implements Serializable {
  private String dataSource;
  private String extractType;
  private String extractExpress;
  private String expectRelation;
  private String expectValue;
  private Integer regexNo;
  private String realType;
  private String realValue;
  private Boolean result;
  private static final long serialVersionUID = 1L;
}