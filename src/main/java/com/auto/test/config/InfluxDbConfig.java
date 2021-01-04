package com.auto.test.config;

import com.auto.test.utils.InfluxDbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class InfluxDbConfig {
  
  @Value("${spring.influx.url:''}")
  private String influxDBUrl;
  @Value("${spring.influx.user:''}")
  private String userName;
  
  @Value("${spring.influx.password:''}")
  private String password;
  
  @Value("${spring.influx.database:''}")
  private String database;
  
  @Value("${rabbit.isPressTest:''}")
  private String isPressTest;
  
  @Bean
  public InfluxDbUtils influxDbUtils() {
    if (isPressTest.equals("true")) {
      return new InfluxDbUtils(userName, password, influxDBUrl, database, "jmeterMonitor");
    } else {
      return null;
    }
  }
}