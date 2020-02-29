package com.dj.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.mapper.MaintainMapper;
import com.dj.pms.mapper.RoleMapper;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Role;
import com.dj.pms.pojo.RoleResource;
import com.dj.pms.pojo.UserRole;
import com.dj.pms.service.MaintainService;
import com.dj.pms.service.RoleResourceService;
import com.dj.pms.service.RoleService;
import com.dj.pms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)//碰到异常做回滚
public class MaintainServiceImpl extends ServiceImpl<MaintainMapper, Maintain> implements MaintainService {

    @Autowired
    private MaintainMapper maintainMapper;


    @Override
    public List<Maintain> findAllMaintain(Integer status) throws Exception {
        return maintainMapper.findAllMaintain(status);
    }
}
