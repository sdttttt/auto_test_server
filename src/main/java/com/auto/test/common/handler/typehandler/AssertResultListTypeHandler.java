package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.AssertResult;

public class AssertResultListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return AssertResult.class;
  }
}
