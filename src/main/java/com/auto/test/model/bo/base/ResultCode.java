package com.auto.test.model.bo.base;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
  
  SUCCESS(200, "操作成功"),
  FAILED(500, "操作失败"),
  GET_CONNECT_ERROR(432, "获取连接异常"),
  REQUEST_NOT_SUPPORT(400, "请求无效"),
  UNAUTHORIZED(401, "暂未登录或token已经过期"),
  VALIDATE_FAILED(402, "参数检验失败"),
  FORBIDDEN(403, "没有相关权限"),
  NOT_FOUND_FAILED(404, "未找到资源"),
  REQUEST_PARA_ERROR(405, "缺少接口要求必填参数"),
  NOT_REQUESTED_DATA(406, "未能读取请求数据"),
  GET_NODE_ERROR(435, "查询Node失败."),
  DATA_NOT_REQUEST(407, "k8s配置数据错误,请检查是否正确"),
  NOT_ENVNAME_REPEATED(408, "环境名不能重复"),
  K8S_CONNECT_ERROR(409, "k8s连接异常,请检查是否正确"),
  ENV_NOT_EXIST(410, "该环境不存在!"),
  QUERY_POD_FAIL(423, "查询pod失败"),
  GET_NAMESPACE_FAIL(438, "查询Namespace失败"),
  QUERY_CRON_JOB_FAIL(416, "查询CronJob失败!"),
  K8S_CONFIG_ERROR(429, "请正确配置k8s证书"),
  GET_K8S_WEBSOCKET_CONNECT_FAIL(431, "获取k8s websocket连接失败!"),
  QUERY_JOB_FAIL(419, "查询job失败"),
  NOT_FOUND_SOURCE(425, "找不到该资源"),
  MODIFY_NODE_LABEL_STATUS_FAIL(437, "修改节点状态失败"),
  MODIFY_NODE_LABEL_FAIL(420, "修改节点标签失败"),
  NOT_K8S_CONFIG(411, "该环境未配置k8s-config!"),
  READ_K8S_POD_LOG_FAIL(430, "读取k8s pod日志失败"),
  K8S_QUERY_ERROR(412, "k8s配置文件异常,请检查是否正确"),
  QUERY_JOB_ERROR(413, "获取job失败"),
  STRING_TO_JSON_ERROR(433, "字符串转换为json异常,请检查格式."),
  RESOURCE_DETAIL_FAIL(505, "获取资源详情失败"),
  MANY_ENV_FAIL(506, "当前不支持多环境"),
  RESOURCE_CREATE_FAIL(501, "资源创建失败"),
  RESOURCE_SCALE_FAIL(502, "资源扩缩容失败"),
  GET_EVENT_FAIL(443, "查询Event失败"),
  RESOURCE_DELETE_FAIL(503, "资源删除失败"),
  RESOURCE_UPDATE_FAIL(504, "资源更新失败"),
  RESOURCE_CRD_FAIL(507, "操作CRD资源失败");
  private Integer code;
  private String message;
  
  ResultCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
  
  @Override
  public Integer getCode() {
    return code;
  }
  
  @Override
  public String getMessage() {
    return message;
  }
  
}
