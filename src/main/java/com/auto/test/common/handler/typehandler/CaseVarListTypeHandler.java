package com.auto.test.common.handler.typehandler;

import com.auto.test.model.po.CaseVar;

public class CaseVarListTypeHandler extends ListTypeHandler {
  @Override
  public Class getTypeClass() {
    return CaseVar.class;
  }
}
