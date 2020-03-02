package com.dj.pms.controller.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.pms.common.SystemConstant;
import com.dj.pms.pojo.BasicData;
import com.dj.pms.pojo.Maintain;
import com.dj.pms.pojo.Sell;
import com.dj.pms.service.BasicDataService;
import com.dj.pms.service.MaintainService;
import com.dj.pms.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sell/")
public class SellPageController {

    @Autowired
    private SellService sellService;

    @Autowired
    private BasicDataService basicDataService;

    /**
     *  去展示
     */
    @RequestMapping("toShow")
    public String toShow() {
        return "sell/show";
    }

    /**
     *  去展示
     */
    @RequestMapping("toSellUser")
    public String toSellUser() {
        return "sell/sell_user";
    }

    /**
     *  去注册
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model){
        QueryWrapper<BasicData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL_ONE);
        queryWrapper.eq("p_id", 5);
        List<BasicData> basicDataList = basicDataService.list(queryWrapper);
        model.addAttribute("basicDataList", basicDataList);
        return "sell/add";
    }

    /**
     * 去修改
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, Model model) {
        QueryWrapper<Sell> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Sell sell = sellService.getOne(queryWrapper);
        QueryWrapper<BasicData> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("p_id", 5);
        List<BasicData> basicDataList = basicDataService.list(queryWrapper1);
        model.addAttribute("sell", sell);
        model.addAttribute("basicDataList", basicDataList);
        return "sell/update";
    }


}
