package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class GlobalVar implements Serializable {
  private Long envId;
  private String key;
  private String value;
  private String remark;
  private static final long serialVersionUID = 1L;
}