package com.auto.test.myTest;

import cn.hutool.cron.pattern.CronPattern;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockTest {
  
  @Test
  public void test() {
    // 创建Mock对象，参数可以是类或者接口
    List<String> list = mock(List.class);
    
    //设置方法的预期返回值
    when(list.get(0)).thenReturn("zuozewei");
    when(list.get(1)).thenThrow(new RuntimeException("test exception"));
    
    String result = list.get(0);
    
    //验证方法调用
    Mockito.verify(list).get(0);
    
    //断言，list的第一个元素是否是"zuozwei"
    Assert.assertEquals(result, "zuozewei");
    
  }
  @Test
  public void test2() {
    // 创建Mock对象，参数可以是类或者接口
    CronPattern cronPattern = new CronPattern("*/2 * * * * *");
    CronPattern cronPattern2 = new CronPattern("* * * * * ? * f");
    
  }
  
}

