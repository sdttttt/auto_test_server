package com.auto.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix = "rabbit")
public class RabbitConfig {
  
  /**
   * 系统文件根路径
   */
  public static String projectPath = System.getProperty("user.dir");
  /**
   * 文件映射路径
   */
  public static String profile;
  public static String jmeterfile;
  public static String appfile;
  public static String uifile;
  public static String systemfile;
  
  public String getSystemfile() {
    return systemfile;
  }
  
  public void setSystemfile(String systemfile) {
    this.systemfile = projectPath + systemfile + File.separator;
  }
  
  public String getAppfile() {
    return appfile;
  }
  
  public void setAppfile(String appfile) {
    this.appfile = projectPath + appfile + File.separator;
  }
  
  public String getUifile() {
    return uifile;
  }
  
  public void setUifile(String uifile) {
    this.uifile = projectPath + uifile + File.separator;
  }
  
  public String getJmeterfile() {
    return jmeterfile;
  }
  
  public void setJmeterfile(String jmeterfile) {
    this.jmeterfile = projectPath + jmeterfile + File.separator;
  }
  
  public String getProfile() {
    return profile;
  }
  
  public void setProfile(String profile) {
    this.profile = projectPath + profile + File.separator;
  }
  
}
