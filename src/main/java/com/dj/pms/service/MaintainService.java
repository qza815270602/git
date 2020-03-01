package com.dj.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Role;

import java.util.List;

public interface MaintainService extends IService<Maintain> {

    List<Maintain> findAllMaintain(Integer status) throws Exception;

    void updateMaintain(Maintain maintain) throws Exception;
}
