package com.auto.test.service.request;

import com.auto.test.entity.TAutoInterface;
import com.auto.test.model.bo.TApiResult;
import com.auto.test.model.po.ApiParam;

import java.util.List;
import java.util.Map;

public interface RequestExecutorServer {
  TApiResult executeHttpRequest(TAutoInterface tApi, Map<String, Object> gVars, Map<String, Object> caseVars, List<ApiParam> params);
}






