package com.auto.test.service;

import com.alibaba.fastjson.JSONObject;
import com.auto.test.entity.TAutoInterface;
import com.auto.test.entity.TAutoInterfaceClassify;
import com.auto.test.model.dto.InterfaceClassifyParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (TAutoInterfaceClassify)表服务接口
 *
 * @author makejava
 * @since 2020-12-21 15:50:39
 */
public interface TAutoInterfaceService extends IService<TAutoInterface> {
  Boolean swaggerImport(String apiUrl, String moduleId);
  
}