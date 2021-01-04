package com.auto.test.service.request.impl;

import com.auto.test.entity.TAutoInterface;
import com.auto.test.model.bo.TApiResult;
import com.auto.test.model.po.Action;
import com.auto.test.model.po.ApiParam;
import com.auto.test.model.po.Result;
import com.auto.test.model.po.TTestDatabese;
import com.auto.test.service.request.RequestExecutorServer;
import com.auto.test.service.request.TApiService;
import com.auto.test.utils.MyStringUtils;
import com.auto.test.utils.apitest.ApiKeywords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TApiServiceImpl implements TApiService {
  //
//    @Resource
//    private TApiMapper tApiMapper;
//    @Resource
//    private TFileInfoService fileInfoService;
//    @Autowired
//    private TTestDatabeseService testDatabeseService;
  @Resource
  private RequestExecutorServer requestExecutorServer;
  
  @Override
  public TApiResult executeHttpRequest(TAutoInterface api, Map<String, Object> gVars, Map<String, Object> caseVars, List<ApiParam> params) {
    return requestExecutorServer.executeHttpRequest(api, gVars, caseVars, params);
  }
  
  @Override
  public Result runAction(Action action, Map<String, Object> gVars, Map<String, Object> caseVars) {
    action.setParams(MyStringUtils.replaceFromMap(action.getParams(), caseVars));
    StringBuffer errMsg = new StringBuffer();
    try {
      Class<?> keywordsClass = new ApiKeywords().getClass();
      ApiKeywords keywords = (ApiKeywords) keywordsClass.newInstance();
      try {
        Method method;
        Result result = new Result();
        
        method = keywordsClass.getMethod(action.getAction(), String.class, Map.class, TTestDatabese.class);
        result = (Result) method.invoke(keywords, action.getParams(), caseVars, null);
        if (result.getFlag()) {
          log.info(result.getMsg());
          log.info("执行步骤【{}】结束", action.getName());
          return Result.success(result.getMsg());
        } else {
          String errorLog = "执行步骤【" + action.getName() + "】关键字【" + action.getAction() + "】失败：原因：" + result.getMsg();
//                    log.error("执行步骤【{}】关键字【{}】失败：原因：{}", stepUi.getName(), stepUi.getAction(), result.getMsg());
          return Result.fail(result.getMsg(), errorLog);
        }
      } catch (InvocationTargetException e) {
        Throwable targetException = e.getTargetException();
        errMsg = errMsg.append("执行关键字【" + action.getAction() + "】异常:");
        if (targetException instanceof NullPointerException) {
          errMsg.append("请检查参数，有必填项为null值");
        } else if (targetException instanceof NoSuchMethodException) {
          errMsg.append("没有找到关键字方法");
//                } else if (targetException instanceof MySQLSyntaxErrorException) {
//                    errMsg.append("执行的Sql错误");
        } else {
          errMsg.append("未知异常:" + targetException.getMessage());
          targetException.printStackTrace();
        }
        String errorLog = "执行步骤【" + action.getName() + "】关键字【" + action.getAction() + "】失败：" + errMsg.toString();
//                log.error("执行步骤【{}】关键字【{}】失败：{}", stepUi.getName(), stepUi.getAction(), errMsg.toString());
        return Result.fail(errMsg.toString(), errorLog);
      }
    } catch (Exception e) {
      errMsg = errMsg.append("获取关键字【" + action.getAction() + "】异常:");
      if (e instanceof ClassNotFoundException) {
        errMsg.append("获取不到keywordsClass");
      } else if (e instanceof NoSuchMethodException) {
        errMsg.append("没有找到关键字方法");
      } else if (e instanceof InstantiationException) {
        errMsg.append("获取关键字实例异常 ");
      } else if (e instanceof IllegalAccessException) {
        errMsg.append("关键字方法没有访问权限");
      } else {
        errMsg.append("未知异常:" + e.getMessage());
        e.printStackTrace();
      }
//            log.error(errMsg.toString());
      return Result.fail(errMsg.toString(), errMsg.toString());
    }
  }
}












