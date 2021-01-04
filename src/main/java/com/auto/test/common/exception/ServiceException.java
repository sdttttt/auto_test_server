package com.auto.test.common.exception;

import com.auto.test.model.bo.base.ResultCode;

public class ServiceException extends RuntimeException {
  private static final long serialVersionUID = -3135239055465723987L;
  
  public ServiceException() {
    super();
  }
  
  public ServiceException(String message) {
    super(message);
  }
  
  public ServiceException(ResultCode resultCode) {
    super(resultCode.getMessage());
  }
  
  public ServiceException(Throwable cause) {
    super(cause);
  }
}
