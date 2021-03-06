package com.dj.pms.controller;



import com.dj.pms.common.ResultModel;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.Resource;
import com.dj.pms.pojo.Sell;
import com.dj.pms.pojo.User;
import com.dj.pms.pojo.UserRole;
import com.dj.pms.service.ResourceService;
import com.dj.pms.service.UserRoleService;
import com.dj.pms.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ResourceService resourceService;

    /**
     * 登录
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login")
    public ResultModel<Object> login(String userName, String password, HttpSession session) {
        try {
            //判断不为空
            if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
                return new ResultModel<>().error(SystemConstant.NOT_NULL);
            }
//            User user1 = userService.findNameAndPwd(userName, password);
//            //用户是否存在
//            if (user1 == null) {
//                return new ResultModel<>().error(SystemConstant.INPUT_ERROR);
//            }
//            //用户是否激活
//            if (!user1.getStatus().equals(SystemConstant.ONE)) {
//                return new ResultModel<>().error(SystemConstant.ACTIVATE);
//            }
//            //用户是否删除
//            if (user1.getIsDel().equals(SystemConstant.IS_DEL_ZERO)) {
//                return new ResultModel<>().error(SystemConstant.DELETE);
//            }
//            // session存放用户信息
//            session.setAttribute(SystemConstant.SESSION_USER, user1);
//            //存放资源信息
//            List<Resource> resourceList = resourceService.findResource(user1.getId());
//            session.setAttribute(SystemConstant.SESSION_RESOURCE, resourceList);
            // shiro登录方式
            Subject subject = SecurityUtils.getSubject();//得到subject
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            subject.login(token);//访问shiro认证器
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }
    }

    /**
     * 查询盐
     * @param userName
     * @return
     */
    @RequestMapping("findSalt")
    public ResultModel<Object> findSalt(String userName){
        try {
            User user = userService.findSalt(userName);
            if (user == null) {
                return new ResultModel<>().error(SystemConstant.INPUT_ERROR);
            }
            ResultModel resultModel = new ResultModel().success(true);
            resultModel.setData(user.getSalt());
            return resultModel;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR);
        }
    }


    /**
     * 展示
     * @param user
     * @return
     */
    @RequestMapping("show")
    public ResultModel<Object> show(User user, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            PageHelper.startPage(pageNo, SystemConstant.PAGING_THREE);
            List<User> userList = userService.findAllUser(user);
            PageInfo<User> pageInfo = new PageInfo<User>(userList);
            map.put("totalNum", pageInfo.getPages());
            map.put("userList", userList);
            return new ResultModel<>().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     *验证是否有用户
     * @param user
     * @return
     */
    @RequestMapping("findByName")
    public boolean findByName(User user) {
        try {
            User user1 =userService.findByName(user);
            return user1 == null ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


    /**
     * 注册
     * @param user
     * @param userRole
     * @return
     */
    @RequestMapping("add")
    public ResultModel<Object> add(User user, UserRole userRole) {
        try {
            userService.addUserAndUserRole(user, userRole);
            return new ResultModel<>().success(SystemConstant.ADD_YES);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping("update")
    public ResultModel<Object> update(User user) {
        try {
            userService.updateUser(user);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 删除
     * @param user
     * @return
     */
    @RequestMapping("del")
    public ResultModel<Object> del(User user) {
        try {
            userService.delUser(user);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("delByIds")
    public ResultModel<Object> delByIds(Integer[] ids) {
        try {
            userService.delUsers(ids);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 授权
     * @param userRole
     * @return
     */
    @RequestMapping("updateUserRole")
    public ResultModel<Object> updateUserRole(UserRole userRole) {
        try {
            userService.updateUserRole(userRole);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 手机验证码
     * @param phone
     * @return
     */
    @RequestMapping("sendMessage")
    public ResultModel<Object> sendMessage(String phone) {
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            User user = userService.sendMessage(phone);
            if (user == null) {
                return new ResultModel<>().error(SystemConstant.REGISTER);
            }
            resultMap.put("ver", String.valueOf(user.getVerify()));
            resultMap.put("msg", SystemConstant.SUCCESS);
            return new ResultModel<>().success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }

    /**
     * 找回密码
     * @param user
     * @return
     */
    @RequestMapping("find")
    public ResultModel<Object> find(User user) {
        try {
            userService.findPhoneAndVerify(user);
            return new ResultModel<>().success(SystemConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error(SystemConstant.ERROR + e.getMessage());
        }
    }


}
