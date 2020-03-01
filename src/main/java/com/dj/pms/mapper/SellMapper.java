package com.dj.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.pms.pojo.Sell;

import java.util.List;

public interface SellMapper extends BaseMapper<Sell> {

    List<Sell> findAllSell(Sell status) throws Exception;
}
