package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiParam implements Serializable {
  private String key;
  private String value;
  private Integer type;
  private static final long serialVersionUID = 1L;
}