package com.dj.pms.controller;

import com.dj.pms.common.ResultModel;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.service.MaintainService;
import com.dj.pms.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sell/")
public class SellController {

    @Autowired
    private SellService sellService;

    /**
     * 展示
     */
    @RequestMapping("show")
    public ResultModel<Object> show(Integer status) {
        try {
            List<Maintain> maintainList = sellService.findAllMaintain(status);
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
    public ResultModel<Object> add(Maintain maintain) {
        try {
            if (maintain.getMaintainTime() == null || maintain.getMaintainProject() == null) {
                return new ResultModel<>().error(SystemConstant.NOT_NULL);
            }
            sellService.addMaintain(maintain);
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
            sellService.updateMaintain(maintain);
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
    public ResultModel<Object> del(Integer id, Integer isDel) {
        try {
            sellService.delMaintain(id, isDel);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }


}