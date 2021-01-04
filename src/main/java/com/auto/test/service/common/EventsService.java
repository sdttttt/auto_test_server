package com.auto.test.service.common;

import com.auto.test.k8s.dto.SearchParamDTO;
import com.auto.test.k8s.model.Event;
import com.auto.test.k8s.model.Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;

import java.util.List;
import java.util.Set;

public interface EventsService {
  
  /**
   * 根据对象元数据与类型查询事件集合
   *
   * @param v1ObjectMeta
   * @param kind
   * @return
   */
  List<Event> listNamespacedEvent(V1ObjectMeta v1ObjectMeta, String kind);
  
  /**
   * 根据对象元数据与类型查询警告类型事件集合
   *
   * @param v1ObjectMeta
   * @param kind
   * @return
   */
  List<Event> listNamespacedEventByWarning(V1ObjectMeta v1ObjectMeta, String kind);
  
  /**
   * 根据容器组集合,生成事件集合
   *
   * @param v1PodList
   * @return
   */
  Set<Event> generateEventSet(V1PodList v1PodList);
  
  /**
   * 根据容器组集合,生成事件集合
   *
   * @param v1PodList
   * @return
   */
  Set<Event> generateEventSet(List<V1Pod> v1PodList);
}
