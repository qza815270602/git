package com.dj.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.pms.pojo.BasicData;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Sell;

import java.util.List;

public interface SellService extends IService<Sell> {

    List<Maintain> findAllMaintain(Integer status) throws Exception;
}
