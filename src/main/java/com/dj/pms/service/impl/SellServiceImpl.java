package com.dj.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.mapper.BasicDataMapper;
import com.dj.pms.mapper.SellMapper;
import com.dj.pms.pojo.BasicData;
import com.dj.pms.pojo.Sell;
import com.dj.pms.pojo.SellUser;
import com.dj.pms.pojo.User;
import com.dj.pms.service.BasicDataService;
import com.dj.pms.service.SellService;
import com.dj.pms.service.SellUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)//碰到异常做回滚
public class SellServiceImpl extends ServiceImpl<SellMapper, Sell> implements SellService {

    @Autowired
    private SellMapper sellMapper;

    @Autowired
    private SellUserService sellUserService;

    @Override
    public List<Sell> findAllSell(Integer isDel) throws Exception {
        return sellMapper.findAllSell(isDel);
    }

    @Override
    public void addSell(Sell sell) throws Exception {
        this.save(sell);
    }

    @Override
    public void updateStatus(Integer id, Integer isDel) throws Exception {
        UpdateWrapper<Sell> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", isDel);
        updateWrapper.in("id", id);
        this.update(updateWrapper);
    }

    @Override
    public List<Sell> findAllSellUser(Integer userId, Integer roleId) throws Exception {
        return sellMapper.findAllSellUser(userId, roleId);
    }

    @Override
    public void updateById(Integer id) throws Exception {
        UpdateWrapper<SellUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", SystemConstant.IS_DEL_ZERO);
        updateWrapper.eq("sell_id", id);
        sellUserService.update(updateWrapper);
    }
}
