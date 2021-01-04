package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.Assert;

public class AssertListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return Assert.class;
  }
}
