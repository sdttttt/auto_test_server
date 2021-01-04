package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Extract implements Serializable {
  private String dataSource;
  private String extractExpress;
  private String varName;
  private static final long serialVersionUID = 1L;
}