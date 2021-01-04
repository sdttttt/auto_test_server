package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.Query;

public class QueryListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return Query.class;
  }
}
