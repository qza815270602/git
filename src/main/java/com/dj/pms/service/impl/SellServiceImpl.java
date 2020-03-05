package com.dj.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public List<Sell> findAllSell(Integer isDel, String sellName) throws Exception {
        return sellMapper.findAllSell(isDel, sellName);
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
    public void updateBySellId(Integer sellId, Integer repertory, Integer count) throws Exception {
        QueryWrapper<SellUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sell_id", sellId);
        sellUserService.remove(queryWrapper);
        UpdateWrapper<Sell> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.set("repertory", repertory + count);
        updateWrapper1.eq("id", sellId);
        this.update(updateWrapper1);
    }
}
