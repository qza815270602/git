package com.dj.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.pms.pojo.Sell;
import com.dj.pms.pojo.SellUser;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface SellUserService extends IService<SellUser> {


    void addSellUser(Integer id, HttpSession session) throws Exception;
}
