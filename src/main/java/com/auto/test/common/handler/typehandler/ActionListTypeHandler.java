package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.Action;

public class ActionListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return Action.class;
  }
}
