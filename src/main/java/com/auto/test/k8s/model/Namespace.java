package com.auto.test.k8s.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.kubernetes.client.openapi.models.V1EventList;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "命名空间")
public class Namespace extends Base {
  
  private static final long serialVersionUID = 3886609240081575338L;
  @ApiModelProperty(value = "状态")
  private String phase;
  @ApiModelProperty(value = "条数")
  private Map<String, Integer> listMeta;
  @ApiModelProperty(value = "事件集合")
  private List<Event> events = new ArrayList<>();
  
  public Namespace(V1Namespace v1Namespace) {
    initBase(v1Namespace.getKind(), v1Namespace, V1Namespace::getMetadata);
    this.phase = v1Namespace.getStatus().getPhase();
  }
  
  public void setV1Events(V1EventList v1EventList) {
    if (v1EventList == null || CollectionUtils.isEmpty(v1EventList.getItems())) {
      return;
    }
    v1EventList.getItems().stream().forEach(v1Event -> {
      Event event = new Event(v1Event);
      events.add(event);
    });
    
  }
  
}
