package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.ApiParam;

public class ApiParamListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return ApiParam.class;
  }
}
