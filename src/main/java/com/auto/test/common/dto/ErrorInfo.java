package com.auto.test.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorInfo implements Serializable {
  private static final long serialVersionUID = -7255844485940012100L;
  private int code;
  private String message;
  
  public ErrorInfo(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
