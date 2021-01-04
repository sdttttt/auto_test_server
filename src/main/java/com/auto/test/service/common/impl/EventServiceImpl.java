package com.auto.test.service.common.impl;

import com.auto.test.common.exception.ServiceException;
import com.auto.test.k8s.constant.K8sParam;
import com.auto.test.k8s.constant.K8sParam.ListParam;
import com.auto.test.k8s.dto.SearchParamDTO;
import com.auto.test.k8s.model.Event;
import com.auto.test.model.bo.base.ResultCode;
import com.auto.test.service.common.EventsService;
import com.auto.test.service.common.K8sApiService;
import com.auto.test.utils.K8sSearch;
import com.auto.test.utils.K8sUtils;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl extends K8sSearch implements EventsService {
  
  @Autowired
  K8sApiService k8sService;
  
  @Override
  public List<Event> listNamespacedEvent(V1ObjectMeta v1ObjectMeta, String kind) {
    List<Event> events = new ArrayList<>();
    String namespace = v1ObjectMeta.getNamespace();
    String fieldSelector = K8sUtils.generateSelector(v1ObjectMeta, kind);
    CoreV1Api coreV1Api = k8sService.getCoreV1Api();
    List<V1Event> eventList = null;
    try {
      if (org.springframework.util.StringUtils.isEmpty(namespace)) {
        
        eventList = coreV1Api.listEventForAllNamespaces(ListParam.allowWatchBookmarks, ListParam._continue, fieldSelector, ListParam.labelSelector, 20, ListParam.pretty, ListParam.resourceVersion, 20000, ListParam.watch).getItems();
        
      } else {
        eventList = coreV1Api.listNamespacedEvent(namespace, ListParam.pretty, ListParam.allowWatchBookmarks, ListParam._continue, fieldSelector, ListParam.labelSelector, 20, ListParam.resourceVersion, 20000, ListParam.watch).getItems();
      }
    } catch (ApiException e) {
      throw new ServiceException(ResultCode.GET_EVENT_FAIL);
    }
    setEvent(eventList, events);
    return events;
  }
  
  public static void setEvent(List<V1Event> eventList, List<Event> events) {
    
    if (!eventList.isEmpty()) {
      for (V1Event v1Event : eventList) {
        Event event = new Event(v1Event);
        
        events.add(event);
      }
    }
    
  }
  
  @Override
  public List<Event> listNamespacedEventByWarning(V1ObjectMeta v1ObjectMeta, String kind) {
    List<Event> list = listNamespacedEvent(v1ObjectMeta, kind);
    list = list.stream().filter(e -> {
      return e.getType().contains(K8sParam.WARNING);
    }).collect(Collectors.toList());
    return list;
  }
  
  @Override
  public Set<Event> generateEventSet(List<V1Pod> v1PodList) {
    Set<Event> eventList = new HashSet<>();
    if (v1PodList != null && !v1PodList.isEmpty()) {
      for (V1Pod v1Pod : v1PodList) {
        List<Event> eventListItem = listNamespacedEventByWarning(v1Pod.getMetadata(), null);
        eventList.addAll(eventListItem);
      }
    }
    return eventList;
  }
  
  @Override
  public Set<Event> generateEventSet(V1PodList v1PodList) {
    return generateEventSet(v1PodList.getItems());
  }
  
}
