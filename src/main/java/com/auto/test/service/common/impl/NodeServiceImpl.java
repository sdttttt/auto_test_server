package com.auto.test.service.common.impl;

import com.auto.test.k8s.constant.K8sParam.*;
import com.alibaba.fastjson.JSONObject;
import com.auto.test.common.exception.ServiceException;
import com.auto.test.k8s.constant.K8sPatchMirror;
import com.auto.test.k8s.dto.NodeDTO;
import com.auto.test.k8s.dto.SearchParamDTO;
import com.auto.test.k8s.model.Event;
import com.auto.test.k8s.model.Node;
import com.auto.test.k8s.model.Pod;
import com.auto.test.k8s.vo.NodeAllocatedResourcesVo;
import com.auto.test.k8s.vo.PatchVo;
import com.auto.test.model.bo.base.Page;
import com.auto.test.model.bo.base.ResultCode;
import com.auto.test.service.common.EventsService;
import com.auto.test.service.common.NodeService;
import com.auto.test.service.common.K8sApiService;
import com.auto.test.service.common.PodService;
import com.auto.test.utils.K8sSearch;
import com.auto.test.utils.K8sUtils;
import com.google.gson.JsonObject;
import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1Pod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class NodeServiceImpl extends K8sSearch implements NodeService {
  @Autowired
  K8sApiService k8sService;
  @Autowired
  PodService podService;
  @Autowired
  EventsService eventsService;
  
  @Override
  public boolean patchNodeLables(NodeDTO nodeDTO, boolean isDelete) {
    CoreV1Api coreV1Api = k8sService.getCoreV1Api();
    String nodeName = nodeDTO.getNodeName();
    try {
      V1Node v1Node = coreV1Api.readNode(nodeName, ReadParam.pretty, ReadParam.exact, ReadParam.export);
      Map<String, String> sourceLables = v1Node.getMetadata().getLabels();
      
      if (isDelete) {
        String[] deleteLabels = nodeDTO.getDeleteLabels();
        if (deleteLabels != null && deleteLabels.length > 0) {
          for (int i = 0; i < deleteLabels.length; i++) {
            String label = deleteLabels[i];
            sourceLables.remove(label);
          }
        }
        
      } else {
        Map<String, String> lables = nodeDTO.getLabels();
        if (lables != null) {
          sourceLables.clear();
          sourceLables.putAll(lables);
        }
        
      }
      List<JsonObject> list = K8sUtils.generatePatchPath(K8sPatchMirror.NODE_LABEL, sourceLables, null);
      
      V1Patch v1Patch = new V1Patch(list.toString());
      V1Node v1NodeReplace = coreV1Api.patchNode(nodeName, v1Patch, PatchParam.pretty, PatchParam.dryRun, PatchParam.fieldManager, PatchParam.force);
      if (v1NodeReplace != null) {
        return true;
      }
    } catch (ApiException e) {
      throw new ServiceException(ResultCode.MODIFY_NODE_LABEL_FAIL);
    }
    return false;
  }
  
  @Override
  public boolean scheduleNode(String nodeName, boolean isSchedule) {
    PatchVo patchVo = new PatchVo("add", K8sPatchMirror.NODE_UNSCHEDULABLE, isSchedule);
    CoreV1Api coreV1Api = k8sService.getCoreV1Api();
    
    try {
      V1Patch v1Patch = new V1Patch(JSONObject.toJSONString(patchVo));
      V1Node v1Node = coreV1Api.patchNode(nodeName, v1Patch, PatchParam.pretty, PatchParam.dryRun, PatchParam.fieldManager, PatchParam.force);
      if (v1Node != null) {
        return true;
      }
    } catch (ApiException e) {
      throw new ServiceException(ResultCode.MODIFY_NODE_LABEL_STATUS_FAIL);
    }
    
    return false;
  }
  
  @Override
  public Page<List<Node>> listNode(SearchParamDTO vo) {
    CoreV1Api coreV1Api = k8sService.getCoreV1Api();
    List<V1Node> items = null;
    try {
      items = coreV1Api.listNode(ListParam.pretty, ListParam.allowWatchBookmarks, ListParam._continue, ListParam.fieldSelector, ListParam.labelSelector, ListParam.limit, ListParam.resourceVersion, ListParam.timeoutSeconds, ListParam.watch).getItems();
    } catch (ApiException e) {
      throw new ServiceException(ResultCode.GET_NODE_ERROR);
    }
    
    List<V1Node> v1NodeList = pagingOrder(vo, items, V1Node::getMetadata, null);
    List<Node> nodes = new ArrayList<>();
    if (v1NodeList != null && !v1NodeList.isEmpty()) {
      v1NodeList.stream().forEach(v1Node -> {
        List<V1Pod> podList = podService.listV1PodByNode(v1Node.getMetadata().getName(), false).getItems();
        Node nodeVo = new Node().initNode(v1Node, new NodeAllocatedResourcesVo(podList, v1Node.getStatus()));
        nodes.add(nodeVo);
      });
    }
    return new Page(vo, nodes, getTotalItem(), new Long(nodes.size()));
  }
  
  @Override
  public Node readNode(String nodeName) {
    CoreV1Api coreV1Api = k8sService.getCoreV1Api();
    V1Node v1Node = null;
    try {
      v1Node = coreV1Api.readNode(nodeName, ReadParam.pretty, ReadParam.exact, ReadParam.export);
    } catch (ApiException e) {
      throw new ServiceException(ResultCode.GET_NODE_ERROR);
    }
    if (v1Node == null) {
      return null;
    }
    String v1NodeName = v1Node.getMetadata().getName();
    List<V1Pod> podList = podService.listV1PodByNode(v1NodeName, false).getItems();
    Node nodeVo = new Node(v1Node, new NodeAllocatedResourcesVo(podList, v1Node.getStatus()));
    List<Pod> pods = new ArrayList<>();
    //先过滤用户命名空间
    // String namespaces = k8sService.getUserNameSpaceStr();
    // podList = filterByNamespaces(podList, V1Pod::getMetadata,namespaces);
    if (!podList.isEmpty()) {
      
      podList.forEach(v1Pod -> {
        Pod pod = new Pod().initPod(v1Pod);
        pods.add(pod);
      });
    }
    //  nodeVo.setPodList(pods);
    List<Event> events = eventsService.listNamespacedEvent(v1Node.getMetadata(), null);
    nodeVo.setEventList(events);
    return nodeVo;
  }
  
}
