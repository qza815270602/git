package com.dj.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.pms.pojo.Resource;
import com.dj.pms.pojo.Role;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {

    List<Resource> findResource(Integer id) throws Exception;
}
