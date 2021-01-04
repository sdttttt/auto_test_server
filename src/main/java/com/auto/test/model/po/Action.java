package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Action implements Serializable {
  private String name;
  private String actionType;
  private String action;
  private String actionRemark;
  private Long mainParamId;
  private String params;
  private static final long serialVersionUID = 1L;
}