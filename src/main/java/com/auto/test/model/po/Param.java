package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Param implements Serializable {
  private String name;
  private String description;
  private static final long serialVersionUID = 1L;
}