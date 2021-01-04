package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.WebHeader;

public class HeaderListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return WebHeader.class;
  }
}
