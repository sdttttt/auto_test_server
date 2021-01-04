package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.Extract;

public class ExtractListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return Extract.class;
  }
}
