package com.dj.pms.controller.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.Resource;
import com.dj.pms.pojo.Role;
import com.dj.pms.service.ResourceService;
import com.dj.pms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resource/")
public class ResourcePageController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("toShow")
    public String toShow(){
        return "resource/show";
    }

    @RequestMapping("toAdd")
    public String toAdd(){
        return "resource/add";
    }


    /**
     * 去修改
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, Model model) {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Resource resource = resourceService.getOne(queryWrapper);
        model.addAttribute("resource", resource);
        return "resource/update";
    }

    /**
     * 去新增
     */
    @RequestMapping("toAdd/{pId}")
    public String toAdd(@PathVariable Integer pId, Model model) {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", pId);
        Resource resource = resourceService.getOne(queryWrapper);
        String pName = pId.equals(SystemConstant.ZERO) ? SystemConstant.SYMBOL:resource.getResourceName();
        model.addAttribute("pName", pName);
        model.addAttribute("pId", pId);
        return "resource/add";
    }
}
