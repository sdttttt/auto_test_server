package com.auto.test.model.constant;

public enum DataSource {
  BODY_JSON("bodyJson"), BODY_XML("bodyXml"), BODY_REG("bodyReg"), CODE("code"), HEADER("header");
  public String value;
  
  DataSource(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
}
