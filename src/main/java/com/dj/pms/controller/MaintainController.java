package com.dj.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.pms.common.ResultModel;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Resource;
import com.dj.pms.pojo.Role;
import com.dj.pms.pojo.RoleResource;
import com.dj.pms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/maintain/")
public class MaintainController {

    @Autowired
    private MaintainService maintainService;

    /**
     * 展示
     */
    @RequestMapping("show")
    public ResultModel<Object> show(Integer status) {
        try {
            List<Maintain> maintainList = maintainService.findAllMaintain(status);
            return new ResultModel<>().success(maintainList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }


    /**
     * 注册
     */
    @RequestMapping("add")
    public ResultModel<Object> add(Role role) {
        try {
//            roleService.addRole(role);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 修改
     */
    @RequestMapping("update")
    public ResultModel<Object> update(Maintain maintain) {
        try {
            maintainService.updateMaintain(maintain);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 删除
     */
    @RequestMapping("del")
    public ResultModel<Object> del(Role role) {
        try {
//            roleService.delRoleAndUserRoleAndRoleResource(role);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }


}