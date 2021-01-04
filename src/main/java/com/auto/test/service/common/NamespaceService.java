package com.auto.test.service.common;

import com.auto.test.k8s.dto.SearchParamDTO;
import com.auto.test.k8s.model.Namespace;
import com.auto.test.model.bo.base.Page;

import java.util.List;

public interface NamespaceService {
  
  /**
   * 获取namespace数据列表
   *
   * @param paramVo
   * @return
   */
  Page<List<Namespace>> getNamespaceList(SearchParamDTO paramVo);
  
  
}
