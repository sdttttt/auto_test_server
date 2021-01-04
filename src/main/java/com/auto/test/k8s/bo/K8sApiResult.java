package com.auto.test.k8s.bo;

import lombok.Data;

@Data
public class K8sApiResult {
  String kind;
  String apiVersion;
  Object metadata;
  String status;
  String message;
  String reason;
  Object details;
  Integer code;
}
