package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.BodyData;

public class BodyDataListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return BodyData.class;
  }
}
