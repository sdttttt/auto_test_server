package com.auto.test.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class JmeterThreadGroup implements Serializable {
  //名称
  private String name;
  //类型：1、普通
  private Integer type = 1;
  //是否启用
  private Boolean isValid;
  //描述
  private String remark;
  //取样器错误后要执行的动作
  private Integer errorActionType;
  //线程数
  private Long threadCount;
  //Ramp-Up时间(秒)
  private Long rampTime;
  //循环次数
  private Long loopCount;
  //是否永远循环
  private Boolean continueForever;
  //是否延迟创建线程
  //调度器
  //持续时间
  //启动延迟
  private static final long serialVersionUID = 1L;
}