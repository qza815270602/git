package com.dj.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.pms.pojo.BasicData;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Sell;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface SellService extends IService<Sell> {

    List<Sell> findAllSell(Integer isDel, String sellName) throws Exception;

    void addSell(Sell sell) throws Exception;

    void updateStatus(Integer id, Integer isDel) throws Exception;

    List<Sell> findAllSellUser(Integer userId, Integer roleId) throws Exception;

    void updateById(Integer id) throws Exception;
}
