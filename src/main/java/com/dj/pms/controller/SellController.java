package com.dj.pms.controller;

import com.dj.pms.common.ResultModel;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Sell;
import com.dj.pms.service.MaintainService;
import com.dj.pms.service.SellService;
import com.dj.pms.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sell/")
public class SellController {

    @Autowired
    private SellService sellService;

    /**
     * 展示
     */
    @RequestMapping("show")
    public ResultModel<Object> show(Sell sell) {
        try {
            List<Sell> sellList = sellService.findAllSell(sell);
            return new ResultModel<>().success(sellList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    @RequestMapping("updateImg")
    public ResultModel<Object> updateImg(MultipartFile file, Sell sell) {
        try {
            if (null == sell.getMaintainProject() || StringUtils.isEmpty(sell.getColour())
                    || StringUtils.isEmpty(sell.getSellName())
                    || StringUtils.isEmpty(sell.getSellPrice())) {
                return new ResultModel<>().error(SystemConstant.NOT_NULL);
            }
            String fileName = UUID.randomUUID().toString().replace("-", "")
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            //通过inputStream上传文件
            InputStream inputStream = file.getInputStream();
            //调用七牛云工具类中的上传方法
            QiniuUtil.uploadByInputStream(inputStream, fileName);
            //将图片信息保存到数据库中
            sell.setImg(fileName);
            sellService.save(sell);
            return new ResultModel<Object>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<Object>().error(SystemConstant.ERROR + e.getMessage());
        }
    }


    /**
     * 注册
     */
    @RequestMapping("add")
    public ResultModel<Object> add(Sell sell) {
        try {
            if (null == sell.getMaintainProject() || StringUtils.isEmpty(sell.getColour())
                    || StringUtils.isEmpty(sell.getSellName())
                    || StringUtils.isEmpty(sell.getSellPrice())) {
                return new ResultModel<>().error(SystemConstant.NOT_NULL);
            }
            sellService.addSell(sell);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

//    /**
//     * 修改
//     */
//    @RequestMapping("update")
//    public ResultModel<Object> update(Maintain maintain) {
//        try {
//            sellService.updateMaintain(maintain);
//            return new ResultModel<>().success(SystemConstant.SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
//        }
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("del")
//    public ResultModel<Object> del(Integer id, Integer isDel) {
//        try {
//            sellService.delMaintain(id, isDel);
//            return new ResultModel<>().success(SystemConstant.SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
//        }
//    }
//

}