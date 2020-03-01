package com.dj.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.pms.mapper.SellMapper;
import com.dj.pms.mapper.SellUserMapper;
import com.dj.pms.pojo.Sell;
import com.dj.pms.pojo.SellUser;
import com.dj.pms.service.SellService;
import com.dj.pms.service.SellUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)//碰到异常做回滚
public class SellUserServiceImpl extends ServiceImpl<SellUserMapper, SellUser> implements SellUserService {

    @Autowired
    private SellUserMapper sellUserMapper;


}
