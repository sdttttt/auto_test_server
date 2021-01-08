package com.auto.test.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.Model;
import io.swagger.models.properties.Property;
import io.swagger.models.utils.PropertyModelConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (TAutoModel)实体类
 *
 * @author makejava
 * @since 2020-12-29 16:24:52
 */
@Data
@TableName(value = "t_auto_model", autoResultMap = true)
public class TAutoModel implements Serializable {
  private static final long serialVersionUID = -36274950212377566L;
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  
  @TableField(typeHandler = FastjsonTypeHandler.class)
  private JSONObject model;
  
  private String moduleId;
  private String description;
  @ApiModelProperty(value = "类型", required = true,example ="0")
  private Integer type;
  private String title;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;
  
  public TAutoModel(Model model, String moduleId) {
    this.title = model.getTitle();
    this.description = model.getDescription();
    JSONObject jsonObject1 =(JSONObject) JSON.toJSON(model);
    
    this.type = 1;
    this.model = jsonObject1;
    this.moduleId = moduleId;
  }
  
}