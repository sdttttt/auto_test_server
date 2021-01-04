package com.auto.test.utils.apitest;

import com.auto.test.model.po.Result;
import com.auto.test.model.po.TTestDatabese;
import com.auto.test.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ApiKeywords {
  public static Result bufferDate(String data, Map caseVars, TTestDatabese testDatabese) throws Exception {
    String[] split = data.replace("；；", ";;").split(";;");
    String formart = "yyyy-MM-dd HH:mm:ss";
    if (split.length >= 2) {
      formart = split[1];
    } else if (split.length == 0) {
      return Result.fail("请填写正确的参数");
    }
    String varsKey = split[0];
    String varValue = DateUtils.dateTimeNow(formart);
    caseVars.put(split[0], varValue);
    return Result.success("缓存变量成功，变量名：[" + varsKey + "]变量值：[" + varValue + "]");
  }
}
