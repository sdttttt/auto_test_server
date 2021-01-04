package com.auto.test.k8s.model;

import com.auto.test.k8s.vo.NodeAllocatedResourcesVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1NodeAddress;
import io.kubernetes.client.openapi.models.V1NodeCondition;
import io.kubernetes.client.openapi.models.V1NodeStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "节点")
public class Node extends Base {
  
  private static final long serialVersionUID = 7533263759635322150L;
  @ApiModelProperty(value = "是否准备好")
  private boolean ready;
  
  private NodeAllocatedResourcesVo allocatedResources;
  @ApiModelProperty(value = "系统信息")
  private NodeSystemInfo nodeInfo;
  @ApiModelProperty(value = "容器组 CIDR")
  private String podCIDR = null;
  
  @ApiModelProperty(value = "提供者id")
  private String providerID = null;
  @ApiModelProperty(value = "是否无法调度")
  private Boolean unschedulable = null;
  @ApiModelProperty(value = "地址集合")
  private List<NodeAddress> addresses = null;
  
  @ApiModelProperty(value = "节点下的事件")
  private List<Event> eventList = null;//需要手动
  
  public Node() {
  
  }
  
  public Node(V1Node v1Node, NodeAllocatedResourcesVo allocatedResources) {
    initNode(v1Node, allocatedResources);
    this.nodeInfo = new NodeSystemInfo(v1Node.getStatus().getNodeInfo());
    this.podCIDR = v1Node.getSpec().getPodCIDR();
    this.providerID = v1Node.getSpec().getProviderID();
    this.unschedulable = v1Node.getSpec().getUnschedulable();
    
    this.addresses = new NodeAddress(v1Node.getStatus().getAddresses()).getAddresses();
    
  }
  
  public Node initNode(V1Node v1Node, NodeAllocatedResourcesVo allocatedResources) {
    initBase(v1Node.getKind(), v1Node, V1Node::getMetadata);
    this.setReady(v1Node.getStatus());
    this.allocatedResources = allocatedResources;
    return this;
  }
  
  public void setReady(V1NodeStatus status) {
    List<V1NodeCondition> conditions = status.getConditions();
    if (CollectionUtils.isEmpty(conditions)) {
      this.ready = false;
    } else {
      conditions.forEach(condition -> {
        if ("True".equals(condition.getStatus()) && "Ready".equals(condition.getType())) {
          this.ready = true;
        }
      });
    }
    
  }
  
  @ApiModel(value = "节点地址")
  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public class NodeAddress {
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "类型")
    private String type;
    
    @ApiModelProperty(value = "地址集合")
    private List<NodeAddress> addresses = null;
    
    public NodeAddress(V1NodeAddress v1NodeAddress) {
      this.address = v1NodeAddress.getAddress();
      this.type = v1NodeAddress.getType();
    }
    
    public NodeAddress(List<V1NodeAddress> v1NodeAddressList) {
      if (v1NodeAddressList != null && v1NodeAddressList.size() > 0) {
        addresses = new ArrayList<>();
        v1NodeAddressList.stream().forEach(v1NodeAddress -> {
          NodeAddress condition = new NodeAddress(v1NodeAddress);
          this.addresses.add(condition);
        });
      }
    }
  }
}