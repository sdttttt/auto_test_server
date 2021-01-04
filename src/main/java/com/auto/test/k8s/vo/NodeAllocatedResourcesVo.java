package com.auto.test.k8s.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.models.V1NodeStatus;
import io.kubernetes.client.openapi.models.V1Pod;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "节点分配的资源数据")
public class NodeAllocatedResourcesVo extends AllocatedResourcesVo {
  @ApiModelProperty(value = "cpu总量")
  private BigDecimal cpuCapacity = new BigDecimal("0");
  
  @ApiModelProperty(value = "内存总量")
  private BigDecimal memoryCapacity = new BigDecimal("0");
  @ApiModelProperty(value = "pod总量")
  private BigDecimal podCapacity = new BigDecimal("0");
  @ApiModelProperty(value = "已分配")
  private Integer allocatedPods;
  
  public NodeAllocatedResourcesVo(List<V1Pod> podList, V1NodeStatus v1NodeStatus) {
    super(podList);
    if (podList != null) {
      allocatedPods = podList.size();
    }
    if (v1NodeStatus != null) {
      Map<String, Quantity> capacity = v1NodeStatus.getCapacity();
      if (capacity != null && !capacity.isEmpty()) {
        Quantity cpu = capacity.get("cpu");
        Quantity memory = capacity.get("memory");
        Quantity pods = capacity.get("pods");
        if (cpu != null) {
          cpuCapacity = cpuCapacity.add(cpu.getNumber());
        }
        if (memory != null) {
          memoryCapacity = memoryCapacity.add(memory.getNumber());
        }
        if (pods != null) {
          podCapacity = podCapacity.add(pods.getNumber());
        }
      }
    }
  }
}
