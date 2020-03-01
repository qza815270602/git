package com.dj.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.pms.mapper.BasicDataMapper;
import com.dj.pms.mapper.SellMapper;
import com.dj.pms.pojo.BasicData;
import com.dj.pms.pojo.Sell;
import com.dj.pms.service.BasicDataService;
import com.dj.pms.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)//碰到异常做回滚
public class SellServiceImpl extends ServiceImpl<SellMapper, Sell> implements SellService {

    @Autowired
    private SellMapper sellMapper;

    @Override
    public List<Sell> findAllSell(Sell sell) throws Exception {
        return sellMapper.findAllSell(sell);
    }

    @Override
    public void addSell(Sell sell) throws Exception {
        this.save(sell);
    }
}
