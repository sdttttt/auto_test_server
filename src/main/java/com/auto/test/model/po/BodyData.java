package com.auto.test.model.po;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "BodyData", description = "body键值对.")
public class BodyData implements Serializable {
  private String key;
  private Object value;
  private static final long serialVersionUID = 1L;
  
  public static List<BodyData> json2BodyDataList(JSONObject json) {
    List<BodyData> dataArrayList = new ArrayList<>();
    if (json.isEmpty()) {
      return null;
    }
//    for (Map.Entry<String, Object> entry : json.entrySet()) {
//      BodyData query = new BodyData();
//      query.setKey(entry.getKey());
//      query.setValue((String) entry.getValue());
//      queryList.add(query);
//    }
    BodyData bodyData = new BodyData();
    bodyData.setKey("form");
    bodyData.setValue(json.toJSONString());
    dataArrayList.add(bodyData);
    return dataArrayList;
  }
}