package com.dj.pms.controller.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Role;
import com.dj.pms.pojo.User;
import com.dj.pms.pojo.UserRole;
import com.dj.pms.service.MaintainService;
import com.dj.pms.service.RoleService;
import com.dj.pms.service.UserRoleService;
import com.dj.pms.service.UserService;
import com.dj.pms.utils.PasswordSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/maintain/")
public class MaintainPageController {

    @Autowired
    private MaintainService maintainService;


    /**
     *  去展示
     */
    @RequestMapping("toShow")
    public String toShow() {
        return "maintain/show";
    }

    /**
     *  去注册
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) throws Exception {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_ONE);
//        List<Role> roleList = roleService.list(queryWrapper);
//        model.addAttribute("roleList", roleList);
        //获取盐到前台进行加密
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("salt", salt);
        return "user/add";
    }

    /**
     * 去修改
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, Model model) {
        QueryWrapper<Maintain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Maintain maintain = maintainService.getOne(queryWrapper);
        model.addAttribute("maintain", maintain);
        return "user/update";
    }


}
