package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.GlobalVar;

public class GlobalVarListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return GlobalVar.class;
  }
}
