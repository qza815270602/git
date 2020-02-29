package com.dj.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.pms.pojo.Maintain;

import java.util.List;
import java.util.zip.DataFormatException;


public interface MaintainMapper extends BaseMapper<Maintain> {

    List<Maintain> findAllMaintain(Integer status) throws DataFormatException;
}
