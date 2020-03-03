package com.dj.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.pms.pojo.Sell;

import javax.xml.bind.DataBindingException;
import java.util.List;

public interface SellMapper extends BaseMapper<Sell> {

    List<Sell> findAllSell(Integer isDel, String sellName) throws DataBindingException;

    List<Sell> findAllSellUser(Integer userId, Integer roleId) throws DataBindingException;
}
