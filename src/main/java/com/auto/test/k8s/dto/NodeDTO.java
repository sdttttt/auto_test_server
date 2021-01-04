package com.auto.test.k8s.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class NodeDTO implements Serializable {
  private static final long serialVersionUID = 3886609240081575338L;
  private String nodeName;
  private Map<String, String> labels = new HashMap<>();
  private String[] deleteLabels;
  
  public NodeDTO(String nodeName, Map<String, String> labels) {
    this.nodeName = nodeName;
    this.labels = labels;
  }
  
  public NodeDTO(String nodeName, String[] deleteLabels) {
    this.nodeName = nodeName;
    this.deleteLabels = deleteLabels;
  }
}
