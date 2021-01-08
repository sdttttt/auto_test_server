package com.auto.test.service.common;

import com.auto.test.entity.TAutoJob;
import com.auto.test.k8s.dto.SearchParamDTO;
import com.auto.test.k8s.model.Builder;
import com.auto.test.k8s.model.Job;
import com.auto.test.k8s.vo.JobVo;
import com.auto.test.model.bo.base.Page;
import io.kubernetes.client.openapi.models.V1Job;

import java.util.List;

public interface JobService {
  /**
   * 根据查询对象获取任务集合
   *
   * @param paramVo
   * @return
   */
  Page<JobVo> listJob(SearchParamDTO paramVo);
  
  /**
   * 任务集合转换
   *
   * @param v1JobList
   * @return
   */
  List<Job> generateListJob(List<V1Job> v1JobList);
  
  /**
   * 任务转换
   *
   * @param v1Job
   * @return
   */
  Job toJob(V1Job v1Job);
  
  /**
   * 根据定时任务查询任务集合
   *
   * @param nameSpace
   * @param jobName
   * @return
   */
  List<Job> listJobByCronJob(String nameSpace, List<String> jobName);
  
  /**
   * 读取任务
   *
   * @param nameSpace
   * @param name
   * @return
   */
  Job readJob(String nameSpace, String name);
  
  Boolean deleteJob(String nameSpace, String name);
  
}
