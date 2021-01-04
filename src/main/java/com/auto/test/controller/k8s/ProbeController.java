package com.auto.test.controller.k8s;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auto.test.model.bo.base.JsonResult;
import com.auto.test.service.common.K8sApiService;
import com.auto.test.service.common.K8sService;
import io.kubernetes.client.Discovery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing ManageEnv.
 *
 * @author ctsi-biyi-generator
 */
@RestController
@RequestMapping("test")
@Api(tags = "测试专用")
@Slf4j
public class ProbeController {
  @Autowired
  K8sService k8sService;
  @Autowired
  K8sApiService k8sApiService;
  
  @GetMapping("probe")
  @ApiOperation("探针")
  public JsonResult<String> probe() {
    return JsonResult.success("我还活着");
  }
  
  @GetMapping("url")
  @ApiOperation("url测试")
  public JsonResult<JSONObject> test(String url) {
    String result = k8sService.executeHttpGetBack(url);
    
    return JsonResult.success(JSON.parseObject(result));
  }
  
}
