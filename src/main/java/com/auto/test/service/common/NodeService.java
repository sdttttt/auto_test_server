package com.auto.test.service.common;

import com.auto.test.k8s.dto.NodeDTO;
import com.auto.test.k8s.dto.SearchParamDTO;
import com.auto.test.k8s.model.Node;
import com.auto.test.model.bo.base.Page;

import java.util.List;

public interface NodeService {
  /**
   * 修改节点标签
   *
   * @param nodeDTO
   * @param isDelete
   * @return
   */
  boolean patchNodeLables(NodeDTO nodeDTO, boolean isDelete);
  
  /**
   * 隔离与恢复节点
   *
   * @param nodeName
   * @param isSchedule
   * @return
   */
  boolean scheduleNode(String nodeName, boolean isSchedule);
  
  /**
   * 根据查询对象获取节点集合
   *
   * @param vo
   * @return
   */
  Page<List<Node>> listNode(SearchParamDTO vo);
  
  /**
   * 查询节点
   *
   * @param nodeName
   * @return
   */
  Node readNode(String nodeName);
}
