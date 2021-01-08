package com.auto.test.service.request;

import com.auto.test.entity.TAutoInterface;
import com.auto.test.entity.TApiResult;
import com.auto.test.model.po.Action;
import com.auto.test.model.po.ApiParam;
import com.auto.test.model.po.Result;

import java.util.List;
import java.util.Map;

public interface TApiService {
  
  Result runAction(Action action, Map<String, Object> gVars, Map<String, Object> caseVars);
  
  TApiResult executeHttpRequest(TAutoInterface api, Map<String, Object> gVars, Map<String, Object> caseVars, List<ApiParam> params);
}












