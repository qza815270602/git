package com.dj.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.pms.common.ResultModel;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.*;
import com.dj.pms.service.SellService;
import com.dj.pms.service.SellUserService;
import com.dj.pms.service.UserRoleService;
import com.dj.pms.utils.QiNiuYunUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

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
     *  展示
     *
     * @param isDel
     * @param pageNo
     * @return
     */
    @RequestMapping("show")
    public ResultModel<Object> show(Integer isDel, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            PageHelper.startPage(pageNo, SystemConstant.PAGING_THREE);
            List<Sell> sellList = sellService.findAllSell(isDel);
            PageInfo<Sell> pageInfo = new PageInfo<Sell>(sellList);
            map.put("totalNum", pageInfo.getPages());
            map.put("sellList", sellList);
            return new ResultModel<>().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 展示购买信息
     * @param session
     * @return
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

    /**
     * 注册七牛上传图片
     * @param file
     * @param sell
     * @return
     */
    @RequestMapping("addImg")
    public ResultModel<Object> addImg(MultipartFile file, Sell sell) {
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
     * 修改
     * @param file
     * @param sell
     * @return
     */
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
            if (sell.getImg() == null){
                sell.setImg(QiNiuYunUtil.upload(file));
            }
            UpdateWrapper<Sell> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("sell_name", sell.getSellName());
            updateWrapper.set("img", sell.getImg());
            updateWrapper.set("sell_price", sell.getSellPrice());
            updateWrapper.set("colour", sell.getColour());
            updateWrapper.set("maintain_project", sell.getMaintainProject());
            updateWrapper.eq("id", sell.getId());
            sellService.update(updateWrapper);
            return new ResultModel<Object>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<Object>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     *   购买
     * @param id
     * @param session
     * @return
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
     * @param id
     * @param isDel
     * @return
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
     * @param id
     * @return
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