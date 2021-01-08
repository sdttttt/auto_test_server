package com.auto.test.entity;

import com.auto.test.model.po.AssertResult;
import com.auto.test.model.po.BodyData;
import com.auto.test.model.po.WebHeader;
import com.auto.test.model.po.Query;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_auto_api_result")
public class TApiResult implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  
  private String planLogId;
  
  /**
   * 用例结果id
   */
  private String caseResultId;
  
  /**
   * 步骤id
   */
  private String stepId;
  
  /**
   * 步骤名称
   */
  private String stepName;
  
  /**
   * -1：异常失败，0：断言失败，1：成功,2:跳过
   */
  private Integer resultType;
  
  /**
   * 请求方式
   */
  private String reqMethod;
  
  /**
   * 请求url
   */
  private String reqUrl;
  
  /**
   * 请求头
   */
  private List<WebHeader> reqHeaders;
  
  /**
   * 请求query
   */
  private List<Query> reqQuery;
  
  /**
   * 请求body的类型
   */
  private String reqBodyType;
  
  /**
   * 请求body from-data格式
   */
  private List<BodyData> reqBodyData;
  
  /**
   * 请求body json格式
   */
  private String reqBodyJson;
  
  /**
   * 响应码
   */
  private Integer rspStatusCode;
  
  /**
   * 响应body类型
   */
  private String rspBodyType;
  
  /**
   * 响应体
   */
  private String rspBody;
  
  /**
   * 响应体长度
   */
  private Integer rspBodySize;
  
  /**
   * 响应的header
   */
  private List<WebHeader> rspHeaders;
  
  /**
   * 响应断言
   */
  private List<AssertResult> rspAsserts;
  
  /**
   * 响应时间（毫秒）
   */
  private Long rspTime;
  
  private String exception;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  
}