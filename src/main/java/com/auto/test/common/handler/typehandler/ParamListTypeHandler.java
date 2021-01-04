package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.Param;

public class ParamListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return Param.class;
  }
}
