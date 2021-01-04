package com.auto.test.service.common;

import com.alibaba.fastjson.JSONObject;
import com.auto.test.k8s.dto.K8sYamlDTO;
import com.auto.test.k8s.dto.ScaleYamlDTO;
import com.auto.test.k8s.dto.YamlBaseDTO;

public interface K8sService {
  
  Integer scaleModuleSize(ScaleYamlDTO scaleYamlVo);
  
  boolean createResource(K8sYamlDTO k8SYamlDTO);
  
  String executeHttpGetBack(String url);
  
  void deleteNamespacedResource(YamlBaseDTO yamlBaseDTO);
  
  JSONObject getResourceByNameUseOKHttp(YamlBaseDTO readYamlVo);
  
  void updateResource(K8sYamlDTO yamlVo);
}
