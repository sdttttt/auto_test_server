package com.auto.test.service.impl;

import com.auto.test.dao.TAutoTestCaseInterfaceDao;
import com.auto.test.dao.TStepApiDao;
import com.auto.test.entity.TAutoStepInterface;
import com.auto.test.entity.TAutoTestcase;
import com.auto.test.model.dto.StepApiDto;
import com.auto.test.service.TStepApiService;
import com.auto.test.utils.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TStepApiServiceImpl extends ServiceImpl<TStepApiDao, TAutoStepInterface> implements TStepApiService {
  
  @Resource
  private TStepApiDao tStepApiMapper;
  
  //    @Resource
//    private TTestcaseApiService testcaseApiService;
//    @Autowired
//    private TStepApiDtoMapper tStepApiDtoMapper;
//    @Autowired
//    private TFileInfoService fileInfoService;
//
  @Override
  public void saveSteps(List<StepApiDto> testSteps) {
    
    if (testSteps.size() > 0) {
      int sort = 1;
      for (StepApiDto stepApiDto : testSteps) {
        stepApiDto.setSort(sort);
        sort = sort + 1;
        if (stepApiDto.getId() == null) {
          //  stepApiDto.setUpdateBy(UserUtil.getLoginUser().getUsername());
          //   stepApiDto.setCreateBy(UserUtil.getLoginUser().getUsername());
          tStepApiMapper.insert(stepApiDto);
        } else {
          //   stepApiDto.setUpdateBy(UserUtil.getLoginUser().getUsername());
          tStepApiMapper.updateById(stepApiDto);
        }
      }
    }
  }
  
  @Override
  public Boolean removeByTestCaseId(String testCaseId) {
    QueryWrapper<TAutoStepInterface> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("testcase_id", testCaseId);
    
    return tStepApiMapper.delete(queryWrapper) > 0 ? true : false;
  }
  
}












