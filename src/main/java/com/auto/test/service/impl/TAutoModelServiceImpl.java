package com.auto.test.service.impl;

import com.auto.test.dao.TAutoModelDao;
import com.auto.test.entity.TAutoInterface;
import com.auto.test.entity.TAutoModel;
import com.auto.test.service.TAutoModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TAutoModelServiceImpl extends ServiceImpl<TAutoModelDao, TAutoModel> implements TAutoModelService {

}
