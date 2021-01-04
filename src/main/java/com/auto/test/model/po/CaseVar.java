package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class CaseVar implements Serializable {
  private String name;
  private String value;
  private String description;
  private static final long serialVersionUID = 1L;
}