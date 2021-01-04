package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
  private static final long serialVersionUID = 1L;
  private Boolean flag;
  private String msg;
  private String logMsg;
  
  public Result() {
  }
  
  private static Result build(Boolean flag, String msg, String logMsg) {
    Result result = new Result();
    result.setFlag(flag);
    result.setMsg(msg);
    result.setLogMsg(logMsg);
    return result;
  }
  
  public static Result success(String msg) {
    return build(true, msg, "");
  }
  
  public static Result success(String msg, String logMsg) {
    return build(true, msg, logMsg);
  }
  
  public static Result fail(String msg) {
    return build(false, msg, "");
  }
  
  public static Result fail(String msg, String logMsg) {
    return build(false, msg, logMsg);
  }
  
  public Result(Boolean flag, String msg) {
    this.flag = flag;
    this.msg = msg;
  }
  
  public Result(Boolean flag, String msg, String logMsg) {
    this.flag = flag;
    this.msg = msg;
    this.logMsg = logMsg;
  }
}
