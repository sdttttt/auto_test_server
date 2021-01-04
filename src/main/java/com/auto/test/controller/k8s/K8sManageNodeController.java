package com.auto.test.controller.k8s;

import com.auto.test.k8s.dto.NodeDTO;
import com.auto.test.k8s.dto.SearchParamDTO;
import com.auto.test.k8s.model.Node;
import com.auto.test.model.bo.base.JsonResult;
import com.auto.test.model.bo.base.Page;
import com.auto.test.service.common.NodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "node")
@Api(tags = "执行机管理")
@Slf4j
public class K8sManageNodeController {
  
  @Autowired
  private NodeService nodeResource;
  
  @GetMapping(value = "list")
  @ApiOperation("获取所有的执行机列表")
  public JsonResult<Page<List<Node>>> node(SearchParamDTO paramVo) {
    log.debug("SearchParamDTO: {} ", paramVo);
    paramVo.validateParams();
    Page<List<Node>> list =
        nodeResource.listNode(paramVo);
    return JsonResult.success(list);
  }
//
//  @GetMapping(value = "/detail")
//  @ApiOperation("根据节点名称查看详情")
//  @ApiImplicitParam(name = "nodeName", value = "节点名称", required = true, paramType = "query", dataType = "String")
//  public JsonResult<Node> getNodeDetailInfo(@RequestParam String nodeName) {
//    log.debug("nodeName: {} ", nodeName);
//
//    Node node = nodeResource.readNode(nodeName);
//    return JsonResult.success(node);
//  }
//
  
  @PutMapping(value = "unschedule")
  @ApiOperation("隔离执行机")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nodeName", value = "节点名称", required = true, paramType = "query", dataType = "String")
  })
  public JsonResult<Boolean> unScheduleNode(@RequestParam(value = "nodeName") String nodeName) {
    log.debug("nodeName: {} ", nodeName);
    return JsonResult.success(nodeResource.scheduleNode(nodeName, true));
  }
  
  @PutMapping(value = "resume")
  @ApiOperation("恢复执行机")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nodeName", value = "节点名称", required = true, paramType = "query", dataType = "String")
  })
  public JsonResult<Boolean> resumeScheduleNode(@RequestParam(value = "nodeName") String nodeName) {
    log.debug("nodeName: {} ", nodeName);
    return JsonResult.success(nodeResource.scheduleNode(nodeName, false));
  }
  
  @PostMapping(value = "lables")
  @ApiOperation("修改执行机标签")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nodeName", value = "节点名称", required = true, paramType = "query", dataType = "String")
  })
  public JsonResult<Boolean> replaceNodeLables(@RequestParam(value = "nodeName") String nodeName, @RequestBody Map<String, String> labels) {
    log.debug("nodeName labels: {} ", nodeName, labels);
    NodeDTO nodeDTO = new NodeDTO(nodeName, labels);
    return JsonResult.success(nodeResource.patchNodeLables(nodeDTO, false));
  }
  
  @DeleteMapping(value = "lables")
  @ApiOperation("删除执行机标签")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nodeName", value = "节点名称", required = true, paramType = "query", dataType = "String")
  })
  public JsonResult<Boolean> deleteNodeLables(@RequestParam(value = "nodeName") String nodeName, @RequestBody String[] deleteLabels) {
    log.debug("nodeName deleteLabels: {} ", nodeName, deleteLabels);
    NodeDTO nodeDTO = new NodeDTO(nodeName, deleteLabels);
    return JsonResult.success(nodeResource.patchNodeLables(nodeDTO, true));
  }
}
