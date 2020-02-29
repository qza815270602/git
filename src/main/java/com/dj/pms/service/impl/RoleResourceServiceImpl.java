package com.dj.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.pms.mapper.RoleResourceMapper;
import com.dj.pms.mapper.UserRoleMapper;
import com.dj.pms.pojo.RoleResource;
import com.dj.pms.pojo.UserRole;
import com.dj.pms.service.RoleResourceService;
import com.dj.pms.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)//碰到异常做回滚
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {
}
