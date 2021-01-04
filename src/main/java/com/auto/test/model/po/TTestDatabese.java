package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TTestDatabese implements Serializable {
  /**
   * id
   */
  private Integer id;
  
  /**
   * 数据库名称
   */
  private String name;
  
  /**
   * 数据库连接
   */
  private String connectUrl;
  
  /**
   * 用户
   */
  private String username;
  
  /**
   * 密码
   */
  private String password;
  
  /**
   * 项目id
   */
  private Long projectId;
  
  /**
   * 类型 0 mysql  1 oracle
   */
  private Integer type;
  
  /**
   * 状态 0启用 1禁用
   */
  private Integer status;
  
  /**
   * 备注
   */
  private String remark;
  
  /**
   * createBy
   */
  private String createBy;
  
  /**
   * createTime
   */
  private Date createTime;
  
  /**
   * updateBy
   */
  private String updateBy;
  
  /**
   * updateTime
   */
  private Date updateTime;
  
  private static final long serialVersionUID = 1L;
}