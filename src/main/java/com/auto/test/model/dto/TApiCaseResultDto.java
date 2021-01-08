package com.auto.test.model.dto;

import com.auto.test.entity.TApiCaseResult;
import com.auto.test.entity.TApiResult;
import lombok.Data;

import java.util.List;

@Data
public class TApiCaseResultDto extends TApiCaseResult {
  private static final long serialVersionUID = 1L;
  private List<TApiResult> steps;
}
