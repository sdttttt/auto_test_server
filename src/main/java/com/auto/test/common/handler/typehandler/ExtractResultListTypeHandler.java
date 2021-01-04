package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.ExtractResult;

public class ExtractResultListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return ExtractResult.class;
  }
}
