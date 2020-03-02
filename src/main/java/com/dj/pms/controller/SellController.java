package com.dj.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.pms.common.ResultModel;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Sell;
import com.dj.pms.pojo.User;
import com.dj.pms.pojo.UserRole;
import com.dj.pms.service.MaintainService;
import com.dj.pms.service.SellService;
import com.dj.pms.service.SellUserService;
import com.dj.pms.service.UserRoleService;
import com.dj.pms.utils.QiNiuYunUtil;
import com.dj.pms.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sell/")
public class SellController {

    @Autowired
    private SellService sellService;

    @Autowired
    private SellUserService sellUserService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 展示
     */
    @RequestMapping("show")
    public ResultModel<Object> show(Integer isDel) {
        try {
            List<Sell> sellList = sellService.findAllSell(isDel);
            return new ResultModel<>().success(sellList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 展示购买信息
     */
    @RequestMapping("showSellUser")
    public ResultModel<Object> showSellUser(HttpSession session) {
        try {
            User user = (User) session.getAttribute(SystemConstant.SESSION_USER);
            //用户id查出角色id
            UserRole userRole = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            List<Sell> sellList = sellService.findAllSellUser(user.getId(), userRole.getRoleId());
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

            // 将上传图片 的 参数 和 名字 传给 upload 方法
            //将图片信息保存到数据库中
            sell.setImg(QiNiuYunUtil.upload(file));
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

    /**
     * 购买
     */
    @RequestMapping("addById")
    public ResultModel<Object> addById(Integer id, HttpSession session) {
        try {
            sellUserService.addSellUser(id, session);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 修改
     */
    @RequestMapping("updateStatus")
    public ResultModel<Object> updateStatus(Integer id ,Integer isDel) {
        try {
            sellService.updateStatus(id, isDel);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 退货
     */
    @RequestMapping("updateById")
    public ResultModel<Object> updateById(Integer id) {
        try {
            sellService.updateById(id);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }


}