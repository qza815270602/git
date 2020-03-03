package com.dj.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.pms.common.ResultModel;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.*;
import com.dj.pms.service.*;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 展示
     * @param roleName
     * @return
     */
    @RequestMapping("show")
    public ResultModel<Object> show(String roleName) {
        try {
            List<Role> roleList = roleService.findAllRole(roleName);
            return new ResultModel<>().success(roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 验证是否有角色
     * @param roleName
     * @return
     */
    @RequestMapping("findByName")
    public boolean findByName(String roleName) {
        try {
            Role role = roleService.findByName(roleName);
            return role == null ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 注册
     * @param role
     * @return
     */
    @RequestMapping("add")
    public ResultModel<Object> add(Role role) {
        try {
            roleService.addRole(role);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 修改
     * @param role
     * @return
     */
    @RequestMapping("update")
    public ResultModel<Object> update(Role role) {
        try {
            roleService.updateRole(role);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 删除
     * @param role
     * @return
     */
    @RequestMapping("del")
    public ResultModel<Object> del(Role role) {
        try {
            roleService.delRoleAndUserRoleAndRoleResource(role);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 资源表的关联
     * @param roleId
     * @return
     */
    @RequestMapping("roleResources/{roleId}")
    public ResultModel<Object> roleResources(@PathVariable Integer roleId) {
        try {
            //获取全部的资源表的信息
            QueryWrapper<Resource> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("is_Del",SystemConstant.IS_DEL_ONE);
            List<Resource> resourceList = resourceService.list(queryWrapper1);
            //获取已关联角色的资源的信息
            QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            queryWrapper.eq("is_Del", SystemConstant.IS_DEL_ONE);
            List<RoleResource> roleResource = roleResourceService.list(queryWrapper);
            //new list集合存放信息
            List<Resource> list = new ArrayList<>();
            for (Resource resource : resourceList) {
                //对重新选择的资源的保存
                Resource resource1 = new Resource();
                resource1.setId(resource.getId());
                resource1.setResourceName(resource.getResourceName());
                resource1.setPId(resource.getPId());
                for (RoleResource roleRes : roleResource) {
                    //复选框的回显
                    if (resource.getId().equals(roleRes.getResourceId())) {
                        resource1.setChecked(true);
                        break;
                    }
                }
                list.add(resource1);
            }
            return new ResultModel<>().success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error("服务器异常");
        }
    }

    /**
     * 删除角色原关联的资源保存新关联的资源
     * @param roleId
     * @param resourceIds
     * @return
     */
    @RequestMapping("saveUpdateRole/{roleId}")
    public ResultModel<Object> saveUpdateRole(@PathVariable Integer roleId, Integer[] resourceIds) {
        try {
            roleService.getUserResAndSaveUserResAndUpdUserRes(roleId, resourceIds);
            return new ResultModel<>().success();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }


}