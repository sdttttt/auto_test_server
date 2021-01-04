package com.auto.test.service.common.impl;

import cn.hutool.core.io.IoUtil;
import com.auto.test.config.ApplicationConfig;
import com.auto.test.dao.TAutoManageEnvDao;
import com.auto.test.k8s.constant.K8sConstant;
import com.auto.test.service.common.K8sApiService;
import com.auto.test.utils.K8sManagement;
import com.auto.test.utils.K8sUtils;
import io.kubernetes.client.Discovery;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class K8sApiServiceImpl extends K8sManagement implements K8sApiService {
  @Value("${spring.profiles.active}")
  private String env;
  
  @Autowired
  TAutoManageEnvDao envMapper;
  @Autowired
  ApplicationConfig config;
  
  private static String K8S_YAML_NAME = "k8s-default.yaml";
  private static String K8S_ERROR = "初始化k8s失败";
  
  @Override
  public ApiextensionsV1beta1Api getApiextensionsV1beta1Api() {
    ApiextensionsV1beta1Api v1beta1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      v1beta1Api = getApiextensionsV1beta1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return v1beta1Api;
  }
  
  @Override
  public BatchV1Api getBatchV1Api() {
    BatchV1Api batchV1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      batchV1Api = getBatchV1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return batchV1Api;
  }
  
  @Override
  public EventsV1beta1Api getEventsV1beta1Api() {
    EventsV1beta1Api eventsV1beta1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      eventsV1beta1Api = getEventsV1beta1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return eventsV1beta1Api;
  }
  
  /**
   * 获取资源调度类
   *
   * @return
   */
  @Override
  public ExtensionsV1beta1Api getExtensionsV1beta1Api() {
    ExtensionsV1beta1Api v1beta1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      v1beta1Api = getExtensionsV1beta1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return v1beta1Api;
  }
  
  @Override
  public RbacAuthorizationV1beta1Api getRbacAuthorizationV1beta1Api() {
    RbacAuthorizationV1beta1Api rbacAuthorizationV1beta1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      rbacAuthorizationV1beta1Api = getRbacAuthorizationV1beta1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return rbacAuthorizationV1beta1Api;
    
  }
  
  @Override
  public BatchV1beta1Api getBatchV1beta1Api() {
    BatchV1beta1Api batchV1beta1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      batchV1beta1Api = getBatchV1beta1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return batchV1beta1Api;
  }
  
  /**
   * 获取资源调度类
   *
   * @return
   */
  @Override
  public CoreV1Api getCoreV1Api() {
    CoreV1Api coreV1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      coreV1Api = getCoreV1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return coreV1Api;
  }
  
  @Override
  public ApiClient getApiClient() {
    
    ApiClient apiClient = null;
    try {
      String k8sConfig = getK8sConfig();
      apiClient = init(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return apiClient;
  }
  
  @Override
  public CoreV1Api getCoreV1Api(Integer envId) {
    CoreV1Api coreV1Api = null;
    String k8sConfig = envMapper.selectById(envId).getK8sConfig();
    try {
      coreV1Api = getCoreV1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return coreV1Api;
  }
  
  @Override
  public RbacAuthorizationV1Api getRbacAuthorizationV1Api() {
    RbacAuthorizationV1Api rbacAuthorizationV1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      rbacAuthorizationV1Api = getRbacAuthorizationV1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return rbacAuthorizationV1Api;
  }
  
  @Override
  public BatchV2alpha1Api getBatchV2alpha1Api() {
    BatchV2alpha1Api batchV2alpha1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      batchV2alpha1Api = getBatchV2alpha1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    return batchV2alpha1Api;
  }
  
  @Override
  public String getK8sConfig(Integer envId) {
    String k8sConfig = envMapper.selectById(envId).getK8sConfig();
    
    return k8sConfig;
  }
  
  @Override
  public Boolean isNotProd() {
    if (!StringUtils.isEmpty(env)) {
      
      if (!K8sConstant.PROD.equalsIgnoreCase(env)) {
        return true;
      }
      
    }
    return false;
  }
  
  @Override
  public String getNamespace() {
    return "all,";
  }
  
  @Override
  public Boolean isK8sManage() {
    
    if (isNotProd()) {
      return true;
    }
//    SecurityContext securityContext = SecurityContextHolder.getContext();
//    Authentication authentication = securityContext.getAuthentication();
//
//    Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
//    if (list != null && list.size() > 0) {
//      for (GrantedAuthority grantedAuthority : list) {
//        String authority = grantedAuthority.getAuthority();
//        if (ModuleEnum.MS_K8S_MANAGE.getCode().equals(authority)) {
//          return true;
//        }
//      }
//    }
//
    return false;
  }
  
  @Override
  public Discovery getDiscovery() {
    String k8sConfig = getK8sConfig();
    
    return getDiscovery(k8sConfig);
  }
  
  @Override
  public Discovery getDiscovery(String k8sConfig) {
    Discovery discovery = null;
    try {
      discovery = getDiscovery(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    return discovery;
  }
  
  @Override
  public String getK8sConfig() {
    Integer envId = 1;
    String k8sConfig = "";
    if (envId != null) {
      k8sConfig = getK8sConfig(envId);
    } else {
      //如果没有默认数据,则读取默认的配置文件生成.
      InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(K8S_YAML_NAME);
      k8sConfig = IoUtil.read(inputStream, "utf8");
      
    }
    
    return k8sConfig;
  }
  
  @Override
  public StorageV1beta1Api getStorageV1beta1Api() {
    StorageV1beta1Api storageV1beta1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      storageV1beta1Api = getStorageV1beta1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    return storageV1beta1Api;
  }
  
  /**
   * 获取资源调度类
   *
   * @return
   */
  @Override
  public AppsV1Api getAppsV1Api() {
    AppsV1Api appsV1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      appsV1Api = getAppsV1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return appsV1Api;
  }
  
  @Override
  public StorageV1Api getStorageV1Api() {
    StorageV1Api storageV1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      storageV1Api = getStorageV1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return storageV1Api;
  }
  
  @Override
  public ApiextensionsV1Api getApiextensionsV1Api() {
    ApiextensionsV1Api apiextensionsV1Api = null;
    String k8sConfig = getK8sConfig();
    try {
      apiextensionsV1Api = getApiextensionsV1Api(k8sConfig, config.getDebugger());
    } catch (Exception e) {
      log.error(K8S_ERROR, e);
      
    }
    
    return apiextensionsV1Api;
  }
}
