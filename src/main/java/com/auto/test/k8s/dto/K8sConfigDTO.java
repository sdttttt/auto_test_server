package com.auto.test.k8s.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class K8sConfigDTO implements Serializable {
  
  private static final long serialVersionUID = 9163808214645146335L;
  
  private String serverUrl;
  
  private String certificateAuthorityData;
  
  private String user;
  
  private String clientCertificateData;
  
  private String clientKeyData;
  
}
