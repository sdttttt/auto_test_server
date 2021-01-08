package com.auto.test.k8s.vo;

import com.auto.test.k8s.model.Job;
import com.auto.test.k8s.model.PodInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("活动的任务")
public class ActiveJobVo implements Serializable {
  private static final long serialVersionUID = 3886609240081575338L;
  @ApiModelProperty(value = "条数")
  private Map<String, Integer> listMeta = new HashMap<>();
  @ApiModelProperty(value = "pod信息")
  private PodInfo status = null;
  @ApiModelProperty(value = "任务集合")
  private List<Job> jobs = null;
  
  public ActiveJobVo(List<Job> jobList) {
    this.status = new PodInfo().jobListInitPodInfo(jobList);
    this.jobs = jobList;
    if (jobList != null && !jobList.isEmpty()) {
      this.listMeta.put("totalItems", jobList.size());
    }
  }
}
