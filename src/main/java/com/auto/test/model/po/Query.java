package com.auto.test.model.po;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@ApiModel(value = "Query", description = "请求参数")
public class Query implements Serializable {
  private String key;
  private Object value;
  private static final long serialVersionUID = 1L;
  
  public static List<Query> json2QueryList(JSONObject json) {
    List<Query> queryList = new ArrayList<>();
    if (json.isEmpty()) {
      return null;
    }
//    for (Map.Entry<String, Object> entry : json.entrySet()) {
//      Query query = new Query();
//      query.setKey(entry.getKey());
//      query.setValue(entry.getValue());
//      queryList.add(query);
//    }
    Query query = new Query();
    query.setKey("query");
    query.setValue(json.toJSONString());
    queryList.add(query);
    return queryList;
  }
  
}