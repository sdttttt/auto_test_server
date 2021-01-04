package com.auto.test.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TApiCaseResult implements Serializable {
  private String id;
  
  private String planLogId;
  
  /**
   * 用例集id
   */
  private String suiteResultId;
  
  private String suiteId;
  
  private String suiteName;
  
  /**
   * 用例id
   */
  private String caseId;
  
  /**
   * 用例名称
   */
  private String caseName;
  
  /**
   * 执行状态 0成功 1失败 2跳过
   */
  private Integer status;
  
  private Integer total;
  
  private Integer success;
  
  private Integer failed;
  
  private Integer skipped;
  
  /**
   * 结束时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Date endTime;
  
  /**
   * 备注
   */
  private String remark;
  
  /**
   * createTime
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Date createTime;
  
  private static final long serialVersionUID = 1L;
}