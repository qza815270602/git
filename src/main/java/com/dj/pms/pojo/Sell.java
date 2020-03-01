package com.dj.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sell")
public class Sell {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String sellName;

    /**
     * 是否删除 1正常 0删除
     */
    private Integer isDel;

    /**
     * 头像
     */
    private String img;

    /**
     *价格
     */
    private Double sellPrice;

    /**
     * 颜色
     */
    private String colour;

    /**
     * 维修项目
     */
    private Integer maintainProject;

    /*
     * 上线时间转换展示在修改页面
     */
    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的。
    private String projectShow;



}
